package edu.cnm.deepdive.passphrase.service;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import edu.cnm.deepdive.passphrase.model.entity.Word;
import java.io.IOException;

public class WordDeserializer extends StdDeserializer<Word> {

  protected WordDeserializer(Class<?> vc) {
    super(vc);
  }

  protected WordDeserializer(JavaType valueType) {
    super(valueType);
  }

  protected WordDeserializer(StdDeserializer<?> src) {
    super(src);
  }

  @Override
  public Word deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
      throws IOException, JacksonException {
    String value = jsonParser.getValueAsString();
    Word word = new Word();
    word.setValue(value);
    return word;
  }
}
