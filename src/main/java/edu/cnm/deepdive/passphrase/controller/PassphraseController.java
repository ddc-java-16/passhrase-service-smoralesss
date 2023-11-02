package edu.cnm.deepdive.passphrase.controller;

import edu.cnm.deepdive.passphrase.model.entity.Passphrase;
import edu.cnm.deepdive.passphrase.service.AbstractPassphraseService;
import edu.cnm.deepdive.passphrase.service.AbstractUserService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/passphrases")
public class PassphraseController {

  private final AbstractUserService userService;
  private final AbstractPassphraseService passphraseService;

  @Autowired
  public PassphraseController(
      AbstractUserService userService, AbstractPassphraseService passphraseService) {
    this.userService = userService;
    this.passphraseService = passphraseService;
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Passphrase> get() {
    return passphraseService.readAll(userService.getCurrentUser());
  }

  @GetMapping(value = "/{key}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Passphrase get(@PathVariable UUID key) {
    return passphraseService.read(userService.getCurrentUser(), key);
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, params = {"q"})
  public List<Passphrase> search(@RequestParam("q") String fragment) {
    return passphraseService.search(userService.getCurrentUser(), fragment);
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Passphrase> post(@Valid @RequestBody Passphrase passphrase) {
    Passphrase created = passphraseService.create(userService.getCurrentUser(), passphrase);
    URI location = WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(PassphraseController.class)
                .get(created.getKey())
        )
        .toUri();
    return ResponseEntity.created(location)
        .body(created);
  }

  @DeleteMapping(value = "/{key}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable UUID key) {
    passphraseService.delete(userService.getCurrentUser(), key);
  }

  @PutMapping(value = "/{key}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public Passphrase put(@PathVariable UUID key, @RequestBody Passphrase passphrase) {
    return passphraseService.update(userService.getCurrentUser(), key, passphrase);
  }

}
