package edu.cnm.deepdive.passphrase.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.time.Instant;
import java.util.UUID;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.lang.NonNull;

@Entity
public class Attachment {

  @NonNull
  @Id
  @Column(name = "attachment_id", updatable = false)
  @GeneratedValue
  @JsonIgnore
  private Long id;

  @NonNull
  @Column(name = "external_key", nullable = false, updatable = false)
  @JsonProperty(access = Access.READ_ONLY)
  private UUID key;

  @NonNull
  @Column(nullable = false, updatable = false)
  @Temporal(TemporalType.TIMESTAMP)
  @CreationTimestamp
  @JsonProperty(access = Access.READ_ONLY)
  private Instant created;

  @NonNull
  @Column(nullable = false, updatable = false)
  @JsonProperty(access = Access.READ_ONLY)
  private String filename;

  @NonNull
  @Column(nullable = false, updatable = false)
  @JsonProperty(access = Access.READ_ONLY)
  private String contentType;

  @Column(nullable = false, updatable = false)
  @JsonProperty(access = Access.READ_ONLY)
  private long size;

  @NonNull
  @Column(nullable = false, updatable = false)
  @JsonIgnore
  private String storageKey;

  @NonNull
  @JoinColumn(name = "passphrase_id", nullable = false, updatable = false)
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  private Passphrase passphrase;


  @NonNull
  public Long getId() {
    return id;
  }

  @NonNull
  public UUID getKey() {
    return key;
  }

  @NonNull
  public Instant getCreated() {
    return created;
  }

  @NonNull
  public String getFilename() {
    return filename;
  }

  public void setFilename(@NonNull String filename) {
    this.filename = filename;
  }

  @NonNull
  public String getContentType() {
    return contentType;
  }

  public void setContentType(@NonNull String contentType) {
    this.contentType = contentType;
  }

  public long getSize() {
    return size;
  }

  public void setSize(long size) {
    this.size = size;
  }

  @NonNull
  public String getStorageKey() {
    return storageKey;
  }

  public void setStorageKey(@NonNull String storageKey) {
    this.storageKey = storageKey;
  }

  @NonNull
  public Passphrase getPassphrase() {
    return passphrase;
  }

  public void setPassphrase(@NonNull Passphrase passphrase) {
    this.passphrase = passphrase;
  }

  @PrePersist
  private void generateKey() {
    key = UUID.randomUUID();
  }

}
