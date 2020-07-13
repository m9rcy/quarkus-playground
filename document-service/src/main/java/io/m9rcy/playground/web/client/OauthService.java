package io.m9rcy.playground.web.client;

import io.m9rcy.playground.web.mapper.OauthClientExceptionMapper;
import io.m9rcy.playground.web.model.response.AccessTokenResponse;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;

@Path("/oauth")
@RegisterRestClient
//@RegisterProvider(value= OauthClientExceptionMapper.class, priority = 50)
public interface OauthService {

    @POST
    @Path("/token")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    AccessTokenResponse token(Form form);
}
