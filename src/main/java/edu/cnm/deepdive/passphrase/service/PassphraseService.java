package edu.cnm.deepdive.passphrase.service;

import edu.cnm.deepdive.passphrase.model.dao.PassphraseRepository;
import edu.cnm.deepdive.passphrase.model.entity.Passphrase;
import edu.cnm.deepdive.passphrase.model.entity.User;
import edu.cnm.deepdive.passphrase.model.entity.Word;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class PassphraseService implements AbstractPassphraseService {

  private final PassphraseRepository repository;
  private final PassphraseProvider provider;

  public PassphraseService(PassphraseRepository repository, PassphraseProvider provider) {
    this.repository = repository;
    this.provider = provider;
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
    List<Word> words = passphrase.getWords();
    if (words.isEmpty()) {
      if (passphrase.getLength() <= 0) {
        throw new IllegalArgumentException(String.format("Invalid length: %d; must be positive.", passphrase.getLength()));
      }
      provider
          .generate(passphrase.getLength())
          .stream()
          .map((str) -> {
            Word word = new Word();
            word.setValue(str);
            return word;
          })
          .forEach(words::add);
    }
    int counter = 0;
    for (Word word : words) {
      word.setOrder(counter++);
      word.setPassphrase(passphrase);
    }
    passphrase.setUser(user);
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
