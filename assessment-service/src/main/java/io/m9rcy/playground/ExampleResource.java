package io.m9rcy.playground;

import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Path("/hello")
public class ExampleResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "hello";
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response upload(@Valid FileUploadRequest request) throws IOException {
        String path = System.getProperty("user.home") + File.separator + "uploads";
        File fileDownload = new File(path + request.getFileName());

        byte[] contents = DatatypeConverter.parseBase64Binary(request.getFileContents());
        writeFile(contents, path + File.separator + request.getFileName());
        FileUploadResponse resp = new FileUploadResponse();
        resp.fileId = UUID.randomUUID().toString();
        resp.contentType = request.getFileType();
        resp.fileName = request.getFileName();
        resp.location = path;
        resp.fileReceivedAt = LocalDateTime.now();
        Response.ResponseBuilder response = Response.ok(resp);
        response.header("Content-Disposition", "attachment;filename=" + resp.fileId);
        return response.build();
    }

    // Utility method
    private void writeFile(byte[] content, String filename) throws IOException {
        File file = new File(filename);

        if (!file.exists()) {
            file.createNewFile();
        }
        FileOutputStream fop = new FileOutputStream(file);
        fop.write(content);
        fop.flush();
        fop.close();
    }
}