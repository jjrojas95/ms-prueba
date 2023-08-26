package co.com.bancolombia.textfileadapter.reader;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CSVreader implements Reader {
  private String PATH;
  private final ObjectMapper mapper = new ObjectMapper();
  private final String COLON = ",";
  private List<Field> manyToOneFields;
  private final Class<?> inputType;
  public <T> CSVreader(Class<T> type) {
    var targetAnnotation = type.getAnnotation(TargetFile.class);
    this.PATH = targetAnnotation.path();
    manyToOneFields = Arrays.stream(type.getDeclaredFields())
        .filter(f -> f.isAnnotationPresent(ManyToOne.class))
        .toList();
    this.inputType = type;
  }
  @Override
  public <T> void save(T entity) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.PATH, true))) {
      writer.write(entity.toString() +"\n" );
      List<Field> iterableFields = Arrays.stream(entity.getClass().getDeclaredFields())
        .filter(field -> Arrays.asList(field.getType().getInterfaces()).contains(Collection.class))
        .filter(field -> ((Class<?>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0]).isAnnotationPresent(TargetFile.class))
        .toList();
      List<Field> recursiveFields = Arrays.stream(entity.getClass().getDeclaredFields())
        .filter(field -> field.isAnnotationPresent(TargetFile.class))
        .toList();
      recursiveFields.forEach(field -> {
        try {
          var childEntity = field.get(entity);
          setIdToChildEntityField(entity, childEntity).accept(field);
          CSVreader dataManager = new CSVreader(field.getType());
          dataManager.save(childEntity);
        } catch (IllegalAccessException e) {
          throw new RuntimeException(e);
        }
      });
      iterableFields.forEach(field -> {
        try {
          field.setAccessible(true);
          Class<?> type = (Class<?>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
          Iterable<?> group = (Iterable<?>) field.get(entity);
          CSVreader dataManager = new CSVreader(type);
          group.forEach(childEntity -> {
            Arrays.stream(childEntity.getClass().getDeclaredFields())
              .filter(f -> f.isAnnotationPresent(OneToMany.class))
              .forEach(setIdToChildEntityField(entity, childEntity));
            dataManager.save(childEntity);
          });
        } catch (IllegalAccessException e) {
          throw new RuntimeException(e);
        }
      });
    } catch (IOException e) {
    }
  }
  @Override
  public <T> List<T> findAll() {
    return getBySpec(v -> true, (Class<T>) inputType);
  }
  public <T> List<T> getBy(Predicate<T> specification, Class<T> type) {
    return getBySpec(specification,type);
  }
  private <T> List<T> getBySpec(Predicate<T> specification, Class<T> type) {
    FileReader reader = null;
    try {
      reader = new FileReader(PATH);
    } catch (FileNotFoundException e) {
      System.out.println("File don't find on path: " + PATH);
      return null;
    }
    var buffer = new BufferedReader(reader);
    int counter = 0;
    List<String> headers = new ArrayList<>();
    List<T> result = new ArrayList<>();
    while (true) {
      String line;
      try {
        line = buffer.readLine();
        if (line == null) break;
        if (counter == 0) headers = Arrays.stream(line.split(COLON)).toList();
        else {
          Map<String, Object> textToMap = convertRowToMap(headers, line);
          T row = mapper.convertValue(textToMap, type);
          for (int i = 0; i < manyToOneFields.size(); i++) {
            Field tempField = manyToOneFields.get(i);
            tempField.setAccessible(true);
            List<?> manyToOneResult = manyToOneFn(tempField, row);
            tempField.set(row, manyToOneResult);
          }
          if(specification.test(row)) result.add(row);
        }
      } catch (IOException e) {
        try {
          reader.close();
          buffer.close();
        } catch (IOException ex) {
          throw new RuntimeException(ex);
        }
        throw new RuntimeException(e);
      } catch (NoSuchFieldException e) {
        throw new RuntimeException(e);
      } catch (IllegalAccessException e) {
        throw new RuntimeException(e);
      }
      counter++;
    }
    try {
      reader.close();
      buffer.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return result;
  }

  private Map<String, Object> convertRowToMap(List<String> headers, String line) {
    int columns = headers.size();
    List<String> values = Arrays.stream(line.split(COLON)).toList();
    return IntStream.range(0, columns)
      .boxed()
      .collect(Collectors.toMap(headers::get, values::get));
  }
  private <F extends Field, T> List<?> manyToOneFn(F field, T row) throws NoSuchFieldException, IllegalAccessException {
    Class<?> fieldType = (Class<?>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
    Field manyToOneField = row.getClass().getDeclaredField("id");
    manyToOneField.setAccessible(true);
    Object id = manyToOneField.get(row);
    Optional<Field> OneToManyField = Arrays.stream(fieldType.getDeclaredFields())
      .filter(f -> f.isAnnotationPresent(OneToMany.class))
      .findFirst();
    if (OneToManyField.isEmpty()) throw new RuntimeException();
    String nameOfMatchedField = OneToManyField.get().getName();
    var newReader = new CSVreader(fieldType);
    return newReader.getBySpec(getManyToOneSpec(nameOfMatchedField, id) , fieldType);
  }
  private <E> Predicate<E> getManyToOneSpec(String nameOfMatchedField, Object id) {
    return v -> {
      try {
        Field matchedField = v.getClass().getDeclaredField(nameOfMatchedField);
        matchedField.setAccessible(true);
        return matchedField.get(v).equals(id);
      } catch (IllegalAccessException e) {
        throw new RuntimeException(e);
      } catch (NoSuchFieldException e) {
        throw new RuntimeException(e);
      }
    };
  }

  private <T, C> Consumer<Field> setIdToChildEntityField(T entity, C childEntity) {
    return field -> {
      field.setAccessible(true);
      Field parentIdField;
      try {
        parentIdField = entity.getClass().getDeclaredField("id");
        parentIdField.setAccessible(true);
        field.set(childEntity, parentIdField.get(entity));
      } catch (NoSuchFieldException | IllegalAccessException e) {
        throw new RuntimeException(e);
      }
    };
  }
}
