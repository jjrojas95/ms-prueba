package co.com.bancolombia.textfileadapter.utils;

import co.com.bancolombia.textfileadapter.reader.CSVreader;
import co.com.bancolombia.textfileadapter.reader.Reader;
import co.com.bancolombia.model.common.IAgreggateRoot;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.function.Function;
import java.util.stream.StreamSupport;


public abstract class GenericProjector <I, O extends IAgreggateRoot>{
  private Class<I> dataInputClass;
  private Function<I,O> toEntityFn;
  protected ObjectMapper mapper;

  protected Reader repository;

  public GenericProjector(CSVreader repository, ObjectMapper mapper, Function<I,O> toEntityFn) {
    this.repository = repository;
    mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    mapper.disable(MapperFeature.USE_ANNOTATIONS);
    this.mapper = mapper;
    ParameterizedType genericSuperclass = (ParameterizedType) this.getClass().getGenericSuperclass();
    this.dataInputClass = (Class<I>) genericSuperclass.getActualTypeArguments()[0];
    this.toEntityFn = toEntityFn;
  }

  private O toEntity(I data) {return data != null? toEntityFn.apply(data): null;}

  private List<O> toList(Iterable<I> dataList) {
    return StreamSupport.stream(dataList.spliterator(), false).map(this::toEntity).toList();
  }

  public List<O> findAll() {return toList(repository.findAll());}

  public O save(O entity) {
    I entityData = toData(entity);
    repository.save(entityData);
    return entity;
  }

  private I toData(O entity) {
    return mapper.convertValue(entity, dataInputClass);
  }
}
