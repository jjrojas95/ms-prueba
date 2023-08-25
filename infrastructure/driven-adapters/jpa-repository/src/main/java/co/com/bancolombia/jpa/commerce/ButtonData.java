package co.com.bancolombia.jpa.commerce;

import jakarta.persistence.*;
import lombok.Data;
@Entity
@Table(name = "buttons")
@Data
public class ButtonData {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @Column(name = "name", length = 50)
  private String name;
  @Column(name = "hash", length = 50, nullable = false)
  private String hash;
  @Column(name = "commerce_id")
  private Integer commerceId;
}
