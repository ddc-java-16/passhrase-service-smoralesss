package edu.cnm.deepdive.passphrase.service;

import java.io.IOException;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

  String store(MultipartFile file) throws StorageException, MediaTypeException;

  Resource retrieve(String key) throws StorageException;

  boolean delete(String key) throws StorageException, UnsupportedOperationException, SecurityException;

  class MediaTypeException extends RuntimeException {

    public MediaTypeException() {
    }

    public MediaTypeException(String message) {
      super(message);
    }

    public MediaTypeException(String message, Throwable cause) {
      super(message, cause);
    }

    public MediaTypeException(Throwable cause) {
      super(cause);
    }

  }

  class StorageException extends RuntimeException {

    public StorageException() {
    }

    public StorageException(String message) {
      super(message);
    }

    public StorageException(String message, Throwable cause) {
      super(message, cause);
    }

    public StorageException(Throwable cause) {
      super(cause);
    }

  }

}
