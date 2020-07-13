package io.m9rcy.playground.web.client;

import io.m9rcy.playground.web.filter.ClientOauthRequestFilter;
import io.m9rcy.playground.web.model.request.DocumentMultipartRequest;
import io.m9rcy.playground.web.model.response.DocumentResponse;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/upload")
@RegisterRestClient
@RegisterProvider(ClientOauthRequestFilter.class)
public interface UploadService {

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    DocumentResponse sendMockUpload(@MultipartForm DocumentMultipartRequest requestBody);

}
