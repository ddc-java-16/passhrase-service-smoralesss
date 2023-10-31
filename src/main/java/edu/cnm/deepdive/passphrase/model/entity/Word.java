package edu.cnm.deepdive.passphrase.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import edu.cnm.deepdive.passphrase.service.WordDeserializer;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.springframework.lang.NonNull;

@Entity
@JsonDeserialize(using = WordDeserializer.class)
public class Word {

  @NonNull
  @Id
  @GeneratedValue
  @Column(name = "word_id", updatable = false)
  @JsonIgnore
  private Long id;

  @NonNull
  @Column(name="content", nullable = false, updatable = false)
  @JsonValue
  private String value;

  @NonNull
  @Column(name="position", nullable = false, updatable = false)
  @JsonIgnore
  private int order;

  @NonNull
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "passphrase_id", nullable = false, updatable = false)
  @JsonIgnore
  private Passphrase passphrase;

  @NonNull
  public Long getId() {
    return id;
  }

  @NonNull
  public String getValue() {
    return value;
  }

  public void setValue(@NonNull String value) {
    this.value = value;
  }

  public int getOrder() {
    return order;
  }

  public void setOrder(int order) {
    this.order = order;
  }

  @NonNull
  public Passphrase getPassphrase() {
    return passphrase;
  }

  public void setPassphrase(@NonNull Passphrase passphrase) {
    this.passphrase = passphrase;
  }
}
