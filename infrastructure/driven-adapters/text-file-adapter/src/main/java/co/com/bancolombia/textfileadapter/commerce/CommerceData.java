package co.com.bancolombia.textfileadapter.commerce;

import co.com.bancolombia.textfileadapter.reader.ManyToOne;
import co.com.bancolombia.textfileadapter.reader.TargetFile;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@TargetFile(path = "/Users/jrcortes/Workdir/ms-prueba/data/commerces.txt")
public class CommerceData {
  static private Integer secuenceId = 5;
  @JsonProperty("id")
  private Integer id;
  @JsonProperty("name")
  private String name;
  @JsonProperty("address")
  private String address;
  @JsonProperty("document_number")
  private String documentNumber;
  @JsonProperty("sector")
  private String sector;
  @JsonProperty("document_type")
  private String documentType;
  @ManyToOne
  private List<ButtonData> buttons;

  private void setIdBySecuence() {
    if (id == null) {
      this.id = secuenceId;
      secuenceId++;
    }
  }

  @Override
  public String toString() {
    setIdBySecuence();
    return String.join(",", id.toString(), name, address, documentNumber, sector, documentType);
  }
}
