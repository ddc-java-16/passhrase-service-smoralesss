package edu.cnm.deepdive.passphrase.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PassphraseLengthValidator.class)
@Documented
public @interface ValidPassphraseLength {

  String message() default "{org.hibernate.validator.referenceguide.chapter06.classlevel.ValidPassphraseLength.message}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
