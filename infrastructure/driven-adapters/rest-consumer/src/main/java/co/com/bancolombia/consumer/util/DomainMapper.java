package co.com.bancolombia.consumer.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;

@Component
public class DomainMapper<D, E> {
  private final ObjectMapper ignorePropertiesMapper;

  @SuppressWarnings("unchecked")
  public DomainMapper() {
    ignorePropertiesMapper = new ObjectMapper();
    ignorePropertiesMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    ignorePropertiesMapper.disable(MapperFeature.USE_ANNOTATIONS);
  }
  public D toDomainModel(E entity, Class<D> type) {
    return ignorePropertiesMapper.convertValue(entity, type);
  }
  public E toDataModel(D entity, Class<E> type) {
    return ignorePropertiesMapper.convertValue(entity, type);
  }
}
