package io.m9rcy.playground.application.provider;

import io.m9rcy.playground.domain.model.provider.HashProvider;
import org.mindrot.jbcrypt.BCrypt;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BCryptHashProvider implements HashProvider {

  @Override
  public String hashPassword(String password) {
    return BCrypt.hashpw(password, BCrypt.gensalt());
  }

  @Override
  public boolean checkPassword(String plaintext, String hashed) {
    return BCrypt.checkpw(plaintext, hashed);
  }
}
