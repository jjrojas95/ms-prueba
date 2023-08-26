package co.com.bancolombia.model.commerce;
import co.com.bancolombia.model.common.IAgreggateRoot;

import lombok.Data;

import java.util.List;

@Data
public class Commerce implements IAgreggateRoot {
  private Integer id;
  private String name;
  private String documentNumber;
  private String documentType;
  private List<Button> buttons;
}
