package io.m9rcy.playground.web.filter;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;
import java.io.IOException;
import java.util.Base64;

public class BasicAuthenticationRequestFilter
    implements ClientRequestFilter
{

  private String username;
  private String password;

  public BasicAuthenticationRequestFilter(String username, String password) {
    this.username = username;
    this.password = password;
  }

  @Override
  public void filter(final ClientRequestContext context) throws IOException {
    String token = String.format("%s:%s", this.username, this.password);
    String value = "BASIC " + Base64
            .getEncoder().encodeToString(token.getBytes());
    MultivaluedMap<String, Object> headers = context.getHeaders();
    headers.putSingle(HttpHeaders.AUTHORIZATION, value);
  }
}