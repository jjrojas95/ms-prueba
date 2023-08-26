package co.com.bancolombia.usecase.commerce;

import co.com.bancolombia.model.commerce.Commerce;
import co.com.bancolombia.model.commerce.gateways.CommerceGateway;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ShowCommerceDataByIdUseCase {
  private final CommerceGateway service;
  public Commerce execute(Integer id) {
    return service.findAll().stream().filter(c -> c.getId().equals(id)).findFirst().orElse(null);
  }
}
