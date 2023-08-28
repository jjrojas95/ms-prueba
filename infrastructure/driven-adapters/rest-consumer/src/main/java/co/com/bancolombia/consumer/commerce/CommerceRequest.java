package co.com.bancolombia.consumer.commerce;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class CommerceRequest {
  private Integer id;
  @JsonProperty("document_number")
  private String documentNumber;
  @JsonProperty("document_type")
  private String documentType;
  private String name;
  private String address;
  private List<Button> buttons;
  @Data
  public static class Button {
    private Integer id;
    private String hash;
    private String name;
    private Integer value;

  }
}
