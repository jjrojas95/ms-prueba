package co.com.bancolombia.usecase.commerce;

import co.com.bancolombia.model.commerce.Commerce;
import co.com.bancolombia.model.commerce.gateways.CommerceGateway;
import co.com.bancolombia.model.interfaces.IStringUtils;
import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
public class CreateButtonHashUseCase {
  private final IStringUtils stringManager;
  private final CommerceGateway repository;
  private final int HASH_LENGTH = 6;
  public void execute(Commerce commerce) {
    commerce.getButtons().forEach(button -> button.createHashBy(commerce, stringManager.createRandomStringWith(HASH_LENGTH)));
    repository.save(commerce);
  }
}
