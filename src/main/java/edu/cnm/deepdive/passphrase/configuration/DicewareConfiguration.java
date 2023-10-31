package edu.cnm.deepdive.passphrase.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "diceware")
public class DicewareConfiguration {

  private String wordList;

  /**
   * Returns the classpath-relative path to a file containing a list of words for use in generating
   * random passphrases.
   *
   * @return Classpath-relative path to word list file.
   */
  public String getWordList() {
    return wordList;
  }

  /**
   * Sets the classpath-relative path to a file containing a list of words for use in generating
   * random passphrases.
   *
   * @param wordList Classpath-relative path to word list file.
   */
  public void setWordList(String wordList) {
    this.wordList = wordList;
  }

}
