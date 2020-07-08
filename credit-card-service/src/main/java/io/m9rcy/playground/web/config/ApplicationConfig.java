package io.m9rcy.playground.web.config;

import com.github.slugify.Slugify;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Singleton;

@ApplicationScoped
public class ApplicationConfig {

  @Singleton
  @Produces
  public Slugify slugify() {
    return new Slugify();
  }
}
