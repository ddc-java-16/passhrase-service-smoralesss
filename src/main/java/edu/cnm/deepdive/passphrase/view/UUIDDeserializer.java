package edu.cnm.deepdive.passphrase.view;

import com.fasterxml.jackson.databind.util.StdConverter;
import edu.cnm.deepdive.passphrase.service.UUIDStringifier;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UUIDDeserializer extends StdConverter<String, UUID>
    implements Converter<String, UUID> {

  private final UUIDStringifier stringifier;

  @Autowired
  public UUIDDeserializer(UUIDStringifier stringifier) {
    this.stringifier = stringifier;
  }

  @Override
  public UUID convert(String s) {
    return stringifier.toUUID(s);
  }

}
