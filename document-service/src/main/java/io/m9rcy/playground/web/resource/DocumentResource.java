package io.m9rcy.playground.web.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.m9rcy.playground.application.data.DocumentData;
import io.m9rcy.playground.application.data.DocumentsData;
import io.m9rcy.playground.domain.model.service.DocumentService;
import io.m9rcy.playground.web.client.UploadService;
import io.m9rcy.playground.web.model.request.DocumentMultipartRequest;
import io.m9rcy.playground.web.model.response.DocumentResponse;
import io.m9rcy.playground.web.model.response.DocumentsResponse;
import io.micrometer.core.instrument.MeterRegistry;
import org.apache.tika.Tika;
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
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

@Path("/documents")
public class DocumentResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(DocumentResource.class);

    @Inject
    ObjectMapper objectMapper;

    @Inject
    DocumentService documentService;

    //@Inject
    //FileService fileService;

    @Inject
    Tika tika;

    @Inject
    @RestClient
    UploadService service;

    //@Inject @Channel("documents")
    //Emitter<DocumentData> documents;

    @Inject
    MeterRegistry registry;


    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response upload(@Valid @MultipartForm DocumentMultipartRequest requestBody) throws Exception {

        String mimetype = tika.detect(requestBody.file);
        LOGGER.info("Mime type: {}", mimetype);

        DocumentData documentData = documentService.create(
                requestBody.fileName, requestBody.description, null, requestBody.tags, requestBody.crsId, requestBody.referenceId);
        //documents.send(documentData);

        DocumentResponse response = new DocumentResponse(documentData);

        //return Response.ok(response).status(Response.Status.CREATED).build();
        Supplier<Response> supplierResponse = () -> {
            return Response.ok(response).status(Response.Status.CREATED).build();
        };
        return registry.timer("document.upload").wrap(supplierResponse).get();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response list(@QueryParam("offset") int offset,
                         @QueryParam("limit") int limit,
                         @QueryParam("tag")
                         List<String> tags) throws Exception {
        DocumentsData result = documentService.findDocuments(offset,limit, "123",tags);
        return Response.ok(objectMapper.writeValueAsString(new DocumentsResponse(result))).status(Response.Status.OK).build();
    }

    @GET
    @Path("/{fileId}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response downloadFile(@PathParam("fileId") String fileId) throws Exception {
        File fileDownload = new File("\test");
        Response.ResponseBuilder response = Response.ok((Object) fileDownload);
        response.header("Content-Disposition", "attachment;filename=" + fileId);
        return response.build();
    }

    @PUT
    @Path("/{fileId}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response updateFile(@PathParam("fileId") String fileId) throws Exception {
        return Response.status(Response.Status.NOT_IMPLEMENTED)
                       .entity("Not yet available").build();
    }

    @DELETE
    @Path("/{fileId}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response deleteFile(@PathParam("fileId") String fileId) throws Exception {
        return Response.status(Response.Status.NOT_IMPLEMENTED)
                       .entity("Not yet available").build();
    }

    @GET
    @Path("/{fileId}/info")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response fileInfo(@PathParam("fileId") String fileId) throws Exception {
        return Response.status(Response.Status.NOT_IMPLEMENTED)
                       .entity("Not yet available").build();
    }

    @POST
    @Path("/{fileId}/copy")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response fileCopy(@PathParam("fileId") String fileId) throws Exception {
        return Response.status(Response.Status.NOT_IMPLEMENTED)
                       .entity("Not yet available").build();
    }

    @POST
    @Path("/{fileId}/touch")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response fileTouch(@PathParam("fileId") String fileId) throws Exception {
        return Response.status(Response.Status.NOT_IMPLEMENTED)
                       .entity("Not yet available").build();
    }

    @POST
    @Path("/{fileId}/trash")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response fileTrash(@PathParam("fileId") String fileId) throws Exception {
        return Response.status(Response.Status.NOT_IMPLEMENTED)
                       .entity("Not yet available").build();
    }

    @POST
    @Path("/{fileId}/untrash")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response fileUntrash(@PathParam("fileId") String fileId) throws Exception {
        return Response.status(Response.Status.NOT_IMPLEMENTED)
                       .entity("Not yet available").build();
    }


    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public DocumentResponse uploadMock(@MultipartForm DocumentMultipartRequest requestBody) throws Exception {
        LOGGER.info(requestBody.toString());
        DocumentResponse response = new DocumentResponse();
        response.setFileId(UUID.randomUUID().toString());
        response.setContentType("XXX");
        response.setLocation("http://the.location.of.the.file");
        response.setDescription(requestBody.getDescription());
        response.setReferenceId(requestBody.getReferenceId());
        response.setCrsId(requestBody.getCrsId());
        response.setFileName(requestBody.getFileName());
        return response;
    }
}
