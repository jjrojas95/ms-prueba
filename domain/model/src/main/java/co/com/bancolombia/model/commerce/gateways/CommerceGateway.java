package co.com.bancolombia.model.commerce.gateways;

import co.com.bancolombia.model.commerce.Commerce;

import java.util.List;

public interface CommerceGateway {
  List<Commerce> findAll();
  Commerce save(Commerce commerce);
}
