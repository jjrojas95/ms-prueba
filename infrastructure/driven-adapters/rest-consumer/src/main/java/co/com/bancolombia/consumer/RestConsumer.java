package co.com.bancolombia.consumer;

import co.com.bancolombia.consumer.commerce.CommerceRequest;
import co.com.bancolombia.consumer.commerce.CommerceResponse;
import co.com.bancolombia.consumer.util.DomainMapper;
import co.com.bancolombia.model.commerce.Commerce;
import co.com.bancolombia.model.commerce.gateways.CommerceGateway;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestConsumer implements CommerceGateway {
    private final WebClient client;
    private final DomainMapper<Commerce, CommerceResponse> responseMapper;
    private final DomainMapper<Commerce, CommerceRequest> requestMapper;

  @Override
  public List<Commerce> findAll() {
      return client.get().uri("/commerces").retrieve().bodyToFlux(CommerceResponse.class)
        .map(commerceResponse -> responseMapper.toDomainModel(commerceResponse, Commerce.class))
        .collect(Collectors.toList())
        .block();
  }

  @Override
  public Commerce save(Commerce commerce) {
    Mono<CommerceRequest> request = Mono.just(requestMapper.toDataModel(commerce, CommerceRequest.class));
    CommerceResponse response = client.post().uri("/commerces").body(request, CommerceRequest.class)
      .retrieve()
      .bodyToMono(CommerceResponse.class)
      .log()
      .block();
    return responseMapper.toDomainModel(response, Commerce.class);
  }
}
