package edu.cnm.deepdive.passphrase.service;

import edu.cnm.deepdive.passphrase.model.entity.Attachment;
import edu.cnm.deepdive.passphrase.model.entity.User;
import edu.cnm.deepdive.passphrase.service.StorageService.StorageException;
import java.io.IOException;
import java.util.UUID;
import org.springframework.core.io.Resource;
import org.springframework.lang.NonNull;
import org.springframework.web.multipart.MultipartFile;

public interface AbstractAttachmentService {

  @NonNull
  Iterable<Attachment> readAll(@NonNull User user, @NonNull UUID passphraseKey);

  @NonNull
  Attachment read(@NonNull User user, @NonNull UUID passphraseKey, @NonNull UUID attachmentKey);

  @NonNull
  Resource readContent(@NonNull User user, @NonNull UUID passphraseKey, @NonNull UUID attachmentKey)
      throws StorageException;

  @NonNull
  Attachment store(@NonNull User user, @NonNull UUID passphraseKey, @NonNull MultipartFile file)
      throws StorageException;

  void delete(@NonNull User user, @NonNull UUID passphraseKey, @NonNull UUID attachmentKey);

}
