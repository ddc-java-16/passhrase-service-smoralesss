package edu.cnm.deepdive.passphrase.service;

import java.util.UUID;

public interface UUIDStringifier {

  String toString(UUID value) throws IllegalArgumentException;

  UUID toUUID(String value) throws IllegalArgumentException;

  class DecodeException extends IllegalArgumentException {

    public DecodeException() {
    }

    public DecodeException(String s) {
      super(s);
    }

    public DecodeException(String message, Throwable cause) {
      super(message, cause);
    }

    public DecodeException(Throwable cause) {
      super(cause);
    }
  }

}
