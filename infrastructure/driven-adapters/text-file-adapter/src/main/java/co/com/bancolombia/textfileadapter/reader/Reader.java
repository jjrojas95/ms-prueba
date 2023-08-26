package co.com.bancolombia.textfileadapter.reader;

import java.util.List;

public interface Reader {
  <T> List<T> findAll();
  <T> void save(T entity);
}
