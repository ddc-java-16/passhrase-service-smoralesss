package edu.cnm.deepdive.passphrase.service;

import edu.cnm.deepdive.passphrase.model.dao.UserRepository;
import edu.cnm.deepdive.passphrase.model.entity.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements
    AbstractUserService {

  private final UserRepository repository;

  @Autowired
  public UserService(UserRepository repository) {
    this.repository = repository;
  }

  @Override
  public User getOrCreate(String oauthKey, String displayName) {
    return repository
        .findByOauthKey(oauthKey)
    // TODO: 10/30/23  Update any relevant content in retrieved user.
        .orElseGet(() -> {
          User user = new User();
          user.setOauthKey(oauthKey);
          user.setDisplayName(displayName);
          // TODO: 10/30/23 Set additional fields as necessary.
          return repository.save(user);
        });
  }

  @Override
  public User getCurrentUser() {
    return (User) SecurityContextHolder.getContext()
        .getAuthentication()
        .getPrincipal();
  }

  @Override
  public User updateUser(User received) {
    return repository
        .findById(getCurrentUser().getId())
        .map((user) -> {
          String displayName = received.getDisplayName();
          if (displayName != null) {
            user.setDisplayName(displayName);
          }
          return repository.save(user);
        })
        .orElseThrow();
  }

  @Override
  public Optional<User> get(UUID key, User requester) {
    return repository
        .findByKey(key); // TODO: 10/30/23 Enforce security restriction on which role(s) can see user information.
  }
}
