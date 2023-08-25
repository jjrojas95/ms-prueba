package co.com.bancolombia.api.commerce;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@Builder(toBuilder = true)
public class CreateCommerceResponse {
  private HttpStatus status;
  private String responseCode;
  private String responseDescription;
}
