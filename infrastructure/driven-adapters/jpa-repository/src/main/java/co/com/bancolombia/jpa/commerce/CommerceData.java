package co.com.bancolombia.jpa.commerce;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "commerces")
public class CommerceData {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @Column(name = "name", length = 50)
  private String name;
  @Column(name = "document_number", length = 50, nullable = false)
  private String documentNumber;
  @Column(name = "document_type", length = 2, nullable = false)
  private String documentType;
  @OneToMany(targetEntity = ButtonData.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "commerce_id")
  private List<ButtonData> buttons;
}
