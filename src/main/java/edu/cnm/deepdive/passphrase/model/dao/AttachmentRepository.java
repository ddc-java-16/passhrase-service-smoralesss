package edu.cnm.deepdive.passphrase.model.dao;

import edu.cnm.deepdive.passphrase.model.entity.Attachment;
import edu.cnm.deepdive.passphrase.model.entity.Passphrase;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {

  Optional<Attachment> findByPassphraseAndKey(Passphrase passphrase, UUID key);

  Iterable<Attachment> findAllByPassphraseOrderByCreatedDesc(Passphrase passphrase);
}
