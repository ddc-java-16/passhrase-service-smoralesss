package edu.cnm.deepdive.passphrase.validation;

import edu.cnm.deepdive.passphrase.model.entity.Passphrase;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PassphraseLengthValidator
    implements ConstraintValidator<ValidPassphraseLength, Passphrase> {

  @Override
  public void initialize(ValidPassphraseLength constraintAnnotation) {
    ConstraintValidator.super.initialize(constraintAnnotation);
  }

  @Override
  public boolean isValid(Passphrase passphrase,
      ConstraintValidatorContext constraintValidatorContext) {
    return !passphrase.getWords().isEmpty() || passphrase.getLength() > 0;
  }

}
