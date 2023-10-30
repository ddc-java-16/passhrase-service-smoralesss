package edu.cnm.deepdive.passphrase.service;

import edu.cnm.deepdive.passphrase.model.entity.User;
import java.util.Collection;
import java.util.Collections;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
public class UserConverter implements Converter<Jwt, UsernamePasswordAuthenticationToken> {

  private final AbstractUserService service;

  public UserConverter(AbstractUserService service) {
    this.service = service;
  }

  @Override
  public UsernamePasswordAuthenticationToken convert(Jwt source) {
    Collection<SimpleGrantedAuthority> grants =
        Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
    User user = service.getOrCreate(source.getSubject(), source.getClaimAsString("name"));
    return new UsernamePasswordAuthenticationToken(user, source.getTokenValue(), grants);
  }
}
