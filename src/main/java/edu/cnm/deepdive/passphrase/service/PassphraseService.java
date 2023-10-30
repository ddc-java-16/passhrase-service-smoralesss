package edu.cnm.deepdive.passphrase.service;

import edu.cnm.deepdive.passphrase.model.dao.PassphraseRepository;
import edu.cnm.deepdive.passphrase.model.entity.Passphrase;
import edu.cnm.deepdive.passphrase.model.entity.User;
import java.util.List;
import java.util.UUID;
import java.util.random.RandomGenerator;
import org.springframework.stereotype.Service;

@Service
public class PassphraseService implements AbstractPassphraseService {

  private final PassphraseRepository repository;
  private final RandomGenerator rng;

  public PassphraseService(PassphraseRepository repository, RandomGenerator rng) {
    this.repository = repository;
    this.rng = rng;
  }

  @Override
  public List<Passphrase> readAll(User user) {
    return user.getPassphrases();
  }

  @Override
  public Passphrase read(User user, UUID key) {
    return repository
        .findByUserAndKey(user, key)
        .orElseThrow();
  }

  @Override
  public Passphrase create(User user, Passphrase passphrase) {
    if (!passphrase.getWords().isEmpty()) {
      passphrase.setUser(user);
    } else {
      // TODO: 10/30/23 Generate random passphrase.
    }
    // TODO: 10/30/23 Assign order field in words.
    return repository.save(passphrase);
  }

  @Override
  public void delete(User user, UUID key) {
    repository
        .findByUserAndKey(user, key)
        .ifPresent(repository::delete);
  }

  @Override
  public Passphrase update(User user, UUID key, Passphrase received) {
    return repository
        .findByUserAndKey(user, key)
        .map((passphrase) -> {
          //noinspection ConstantValue
          if (received.getName() != null) {
            passphrase.setName(received.getName());
          }
          if (!received.getWords().isEmpty()) {
            passphrase.getWords().clear();
            passphrase.getWords().addAll(received.getWords());
            // TODO: 10/30/23 Assign order field.
          }
          return repository.save(passphrase);
        })
        .orElseThrow();
  }
}
