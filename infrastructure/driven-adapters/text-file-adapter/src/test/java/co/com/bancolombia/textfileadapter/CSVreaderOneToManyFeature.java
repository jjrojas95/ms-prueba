package co.com.bancolombia.textfileadapter;

import co.com.bancolombia.textfileadapter.reader.CSVreader;
import co.com.bancolombia.textfileadapter.reader.ManyToOne;
import co.com.bancolombia.textfileadapter.reader.OneToMany;
import co.com.bancolombia.textfileadapter.reader.TargetFile;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.springframework.test.util.AssertionErrors.assertFalse;

class CSVreaderOneToManyFeature {
  private static final String PATH_CARTS = "/Users/jrcortes/Workdir/ms-prueba/infrastructure/driven-adapters/text-file-adapter/src/test/java/co/com/bancolombia/textfileadapter/data/carts.txt";
  private static final String PATH_ITEMS = "/Users/jrcortes/Workdir/ms-prueba/infrastructure/driven-adapters/text-file-adapter/src/test/java/co/com/bancolombia/textfileadapter/data/items.txt";
  private List<Cart> carts;
  @BeforeEach
  void setup() throws IOException {
    CSVreader reader = new CSVreader(Cart.class);
    carts = reader.findAll();
  }
  @Test
  void shouldGetDataFromTestFile() throws IOException {
    assertFalse("Shouldn't get empty result", carts.isEmpty());
  }
  @Test
  void shouldGetChildFieldFromTestFile() throws IOException {
    assertFalse("Shouldn't get empty result", carts.get(0).getItems().isEmpty());
  }

  @Getter
  @AllArgsConstructor
  @NoArgsConstructor
  @JsonIgnoreProperties(ignoreUnknown = true)
  @TargetFile(path = PATH_CARTS)
  private static class Cart {
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("name")
    private String name;
    @ManyToOne
    private List<Item> items;
  }
  @Getter
  @AllArgsConstructor
  @NoArgsConstructor
  @JsonIgnoreProperties(ignoreUnknown = true)
  @TargetFile(path = PATH_ITEMS)
  private static class Item {
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("cart_id")
    @OneToMany(columnName = "cartId")
    private Integer cartId;
  }
}
