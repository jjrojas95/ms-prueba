package co.com.bancolombia.textfileadapter;

import co.com.bancolombia.model.commerce.Commerce;
import co.com.bancolombia.textfileadapter.commerce.CommerceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.springframework.test.util.AssertionErrors.*;

public class CommerceServiceTest {
  private CommerceService service;
  @BeforeEach
  void setup(){
    var mapper = new ObjectMapper();
    service = new CommerceService(mapper);
  }
  @Test
  void shouldGetAllCommercesInDomainFormat() throws IOException {
    List<Commerce> commerces = service.findAll();

    assertFalse("Commerce shouldn't be empty list", commerces.isEmpty());
    assertNotEquals("Commerces size shouldn't be zero", 0 , commerces.size());
    assertFalse("Child buttons shouldn't be empty list", commerces.get(0).getButtons().isEmpty());
  }
  @Test
  void shouldGuaranteeIntegrityOfData() throws IOException {
    List<Commerce> commerces = service.findAll();
    String documentNumber = "100000000";

    assertEquals("Commerces Data should be consistent", documentNumber, commerces.get(0).getDocumentNumber());
  }
}
