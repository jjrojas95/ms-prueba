package co.com.bancolombia.textfileadapter;

import co.com.bancolombia.textfileadapter.reader.CSVreader;
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

import static org.springframework.test.util.AssertionErrors.assertEquals;

class CSVreaderDataDistinctToDTOTest {
  private static final String PATH = "/Users/jrcortes/Workdir/ms-prueba/infrastructure/driven-adapters/text-file-adapter/src/test/java/co/com/bancolombia/textfileadapter/data/usersNotMatch.txt";

  private CSVreader reader;
  private List<UserDTO> users;
  @BeforeEach
  void setup(){
    reader = new CSVreader(UserDTO.class);
  }

  @Test
  void shouldGuaranteeIntegrityOfData() throws IOException {
    users = reader.findAll();
    String userNameInRow1 = "juan";
    Integer userAgeInRow2 = Integer.parseInt("26");
    Integer userIdInRow3 = Integer.parseInt("2");
    assertEquals("Integrity of first user's name should guarantee", userNameInRow1, users.get(0).getName());
    assertEquals("Integrity of second user's age should guarantee", userAgeInRow2, users.get(1).getAge());
    assertEquals("Integrity of third user's id should guarantee", userIdInRow3, users.get(2).getId());
  }

  @Test
  void shouldGetDataBySpec() {
    users = reader.getBy(v -> v.getId() == 0, UserDTO.class);
    String name = "juan";
    assertEquals("result size should be ", users.size(), 1);
    assertEquals("User's name should be ", users.get(0).getName(), name);
  }

  @Getter
  @AllArgsConstructor
  @NoArgsConstructor
  @JsonIgnoreProperties(ignoreUnknown = true)
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
