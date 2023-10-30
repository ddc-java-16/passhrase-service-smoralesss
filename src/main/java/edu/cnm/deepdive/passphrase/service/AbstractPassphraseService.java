package edu.cnm.deepdive.passphrase.service;

import edu.cnm.deepdive.passphrase.model.entity.Passphrase;
import edu.cnm.deepdive.passphrase.model.entity.User;
import java.util.List;
import java.util.UUID;

public interface AbstractPassphraseService {

  List<Passphrase> readAll(User user);

  Passphrase read(User user, UUID key);

  Passphrase create(User user, Passphrase passphrase);

  void delete(User user, UUID key);

  Passphrase update(User user, UUID key, Passphrase received);

}
