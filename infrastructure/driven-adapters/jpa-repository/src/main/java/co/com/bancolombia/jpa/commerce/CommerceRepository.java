package co.com.bancolombia.jpa.commerce;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface CommerceRepository extends CrudRepository<CommerceData, Integer>, QueryByExampleExecutor<CommerceData> {
}
