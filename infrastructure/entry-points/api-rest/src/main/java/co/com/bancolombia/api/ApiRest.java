package co.com.bancolombia.api;
import co.com.bancolombia.api.commerce.CreateCommerceResponse;
import co.com.bancolombia.model.commerce.Commerce;
import co.com.bancolombia.usecase.commerce.CreateButtonHashUseCase;
import co.com.bancolombia.usecase.commerce.ShowCommerceDataByIdUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class ApiRest {
  private final ShowCommerceDataByIdUseCase showCommerceDataByIdUseCase;
  private final CreateButtonHashUseCase createButtonHashUseCase;
  @GetMapping(path = "/commerce")
  public Commerce getCustomerById(@RequestParam Integer id) {
    return showCommerceDataByIdUseCase.execute(id);
  }
  @PostMapping(path = "/commerce")
  public CreateCommerceResponse createCustomer(@RequestBody Commerce commerce) {
    createButtonHashUseCase.execute(commerce);
    return CreateCommerceResponse.builder()
      .status(HttpStatus.OK)
      .responseCode("BCCO001")
      .responseDescription("Creado satisfactoriamente")
      .build();
  }
}
