package edu.cnm.deepdive.passphrase.model.dao;

import edu.cnm.deepdive.passphrase.model.entity.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByKey(UUID key);
  Optional<User> findByOauthKey(String oauthKey);

}
