package co.com.bancolombia.textfileadapter.commerce;

import co.com.bancolombia.textfileadapter.reader.CSVreader;
import co.com.bancolombia.textfileadapter.utils.GenericProjector;
import co.com.bancolombia.model.commerce.Commerce;
import co.com.bancolombia.model.commerce.gateways.CommerceGateway;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;



@Repository
public class CommerceService extends GenericProjector<CommerceData, Commerce> implements CommerceGateway {
  private static final CSVreader reader  = new CSVreader(CommerceData.class);

  @Autowired
  public CommerceService(ObjectMapper mapper) {
    super(reader, mapper,  v -> mapper.convertValue(v, Commerce.class));
  }
}
