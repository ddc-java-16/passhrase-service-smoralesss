package edu.cnm.deepdive.passphrase.service;

import java.util.List;

public interface PassphraseProvider {

  List<String> generate(int length);

}
