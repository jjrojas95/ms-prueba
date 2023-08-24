package co.com.bancolombia.model.commerce;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Data
public class Button{
  private Integer id;
  private String hash;
  private String  name;

  public void createHashBy(Commerce commerce, String hash) {

    if (this.hash != null) return;

    if (name == null || name.isBlank() || name.isEmpty()) this.hash = commerce.getName().toLowerCase() + "-" + hash;
    else {
      String capitalName = String.valueOf(name.charAt(0)).toUpperCase() + name.substring(1).toLowerCase();
      this.hash = commerce.getName().toLowerCase() + capitalName + "-" + hash;
    }
  }
}
