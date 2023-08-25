package co.com.bancolombia.jpa.commerce;

import co.com.bancolombia.jpa.helper.AdapterOperations;
import co.com.bancolombia.model.commerce.Commerce;
import co.com.bancolombia.model.commerce.gateways.CommerceGateway;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;

@Repository
public class CommerceRepositoryAdapter extends AdapterOperations<Commerce, CommerceData, Integer, CommerceRepository> implements CommerceGateway
{
    public CommerceRepositoryAdapter(CommerceRepository repository, ObjectMapper mapper) {
        /**
         *  Could be use mapper.mapBuilder if your domain model implement builder pattern
         *  super(repository, mapper, d -> mapper.mapBuilder(d,ObjectModel.ObjectModelBuilder.class).build());
         *  Or using mapper.map with the class of the object model
         */
        super(repository, mapper, d -> mapper.map(d, Commerce.class));
    }
}
