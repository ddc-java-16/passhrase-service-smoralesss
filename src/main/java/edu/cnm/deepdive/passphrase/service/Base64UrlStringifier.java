package edu.cnm.deepdive.passphrase.service;

import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class Base64UrlStringifier implements UUIDStringifier {

  private final Encoder ENCODER = Base64.getUrlEncoder().withoutPadding();
  private final Decoder DECODER = Base64.getUrlDecoder();

  @Override
  public String toString(UUID value) throws IllegalArgumentException {
    String result;
    if (value != null) {
      ByteBuffer buffer = ByteBuffer.wrap(new byte[16]);
      buffer.putLong(value.getMostSignificantBits());
      buffer.putLong(value.getLeastSignificantBits());
      result = ENCODER.encodeToString(buffer.array());
    } else {
      result = null;
    }
    return result;
  }

  @Override
  public UUID toUUID(String value) throws IllegalArgumentException {
    UUID result;
    if (value != null) {
      try {
        ByteBuffer buffer = ByteBuffer.wrap(DECODER.decode(value));
        long mostSignificant = buffer.getLong();
        long leastSignificant = buffer.getLong();
        result = new UUID(mostSignificant, leastSignificant);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    } else {
      result = null;
    }
    return result;
  }

}
