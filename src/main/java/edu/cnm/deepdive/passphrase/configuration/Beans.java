package edu.cnm.deepdive.passphrase.configuration;

import java.security.SecureRandom;
import java.util.random.RandomGenerator;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Beans {

  @Bean
  public RandomGenerator provideRng() {
    return new SecureRandom();
  }

  @Bean
  public ApplicationHome provideHome() {
    return new ApplicationHome();
  }

}
