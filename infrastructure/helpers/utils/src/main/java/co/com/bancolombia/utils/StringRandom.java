package co.com.bancolombia.utils;

import co.com.bancolombia.model.interfaces.IStringUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

@Component
public class StringRandom implements IStringUtils {
  @Override
  public String createRandomStringWith(int length) {
    return RandomStringUtils.randomAlphanumeric(length);
  }
}
