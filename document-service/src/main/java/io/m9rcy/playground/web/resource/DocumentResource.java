package io.m9rcy.playground.web.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.m9rcy.playground.web.client.UploadService;
import io.m9rcy.playground.web.filter.ClientOauthRequestFilter;
import io.m9rcy.playground.web.model.request.DocumentMultipartRequest;
import io.m9rcy.playground.web.model.response.DocumentResponse;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.util.UUID;

@Path("/documents")
public class DocumentResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientOauthRequestFilter.class);

    @Inject
    ObjectMapper objectMapper;

    @Inject
    @RestClient
    UploadService service;

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response upload(@Valid @MultipartForm DocumentMultipartRequest requestBody) throws Exception {
        return Response.ok(service.sendMockUpload(requestBody)).status(Response.Status.OK).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response list() throws Exception {
        return Response.ok("Not yet implemented").status(Response.Status.NOT_IMPLEMENTED).build();
    }

    @GET
    @Path("/{fileId}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response download(@PathParam("fileId") String fileId) throws Exception {
        File fileDownload = new File("\test");
        Response.ResponseBuilder response = Response.ok((Object) fileDownload);
        response.header("Content-Disposition", "attachment;filename=" + fileId);
        return response.build();
    }

    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public DocumentResponse uploadMock(@MultipartForm DocumentMultipartRequest requestBody) throws Exception {
        LOGGER.info(requestBody.toString());
        DocumentResponse response = new DocumentResponse();
        response.setFileId(UUID.randomUUID().toString());
        response.setContentType("");
        response.setDocumentType(requestBody.getDocumentType());
        response.setFileName(requestBody.getFileName());
        return response;
    }
}
