package co.com.bancolombia.textfileadapter;

import co.com.bancolombia.textfileadapter.reader.CSVreader;
import co.com.bancolombia.textfileadapter.reader.TargetFile;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertFalse;

class CSVreaderSameDTOTest {
  private static final String PATH = "/Users/jrcortes/Workdir/ms-prueba/infrastructure/driven-adapters/text-file-adapter/src/test/java/co/com/bancolombia/textfileadapter/data/usuarios.txt";

  private List<UserDTO> users;
  @BeforeEach
  void setup() throws IOException {
    CSVreader reader = new CSVreader(UserDTO.class);
    users = reader.findAll();
  }
  @Test
  void shouldGetDataFromTestFile() throws IOException {
    assertFalse("Shouldn't get empty result", users.isEmpty());
  }

  @Test
  void shouldGuaranteeIntegrityOfData() {
    String userNameInRow1 = "juan";
    Integer userAgeInRow2 = Integer.parseInt("26");
    Integer userIdInRow3 = Integer.parseInt("2");
    assertEquals("Integrity of first user's name should guarantee", userNameInRow1, users.get(0).getName());
    assertEquals("Integrity of second user's age should guarantee", userAgeInRow2, users.get(1).getAge());
    assertEquals("Integrity of third user's id should guarantee", userIdInRow3, users.get(2).getId());
  }

  @Getter
  @AllArgsConstructor
  @NoArgsConstructor
  @TargetFile(path = PATH)
  private static class UserDTO {
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("age")
    private Integer age;
  }
}
