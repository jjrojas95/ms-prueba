package co.com.bancolombia.textfileadapter.commerce;

import co.com.bancolombia.textfileadapter.reader.OneToMany;
import co.com.bancolombia.textfileadapter.reader.TargetFile;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@TargetFile(path = "/Users/jrcortes/Workdir/ms-prueba/data/buttons.txt")
public class ButtonData {
  private static Integer secuenceId = 9;
  @JsonProperty("id")
  private Integer id;
  @JsonProperty("hash")
  private String hash;
  @JsonProperty("name")
  private String name;
  @JsonProperty("commerce_id")
  @OneToMany(columnName = "commerceId")
  private Integer commerceId;

  private void setIdBySecuenceId() {
    if (id == null) {
      id = secuenceId;
      secuenceId++;
    }
  }

  @Override
  public String toString() {
    setIdBySecuenceId();
    return String.join(",", id.toString(), hash, name, commerceId.toString());
  }
}
