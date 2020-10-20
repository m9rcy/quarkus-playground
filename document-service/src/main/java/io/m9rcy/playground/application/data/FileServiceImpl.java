package io.m9rcy.playground.application.data;

import io.m9rcy.playground.domain.model.service.FileService;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.core.Vertx;
import io.vertx.mutiny.core.file.FileSystem;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.InputStream;
import java.util.UUID;

@ApplicationScoped
public class FileServiceImpl implements FileService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileServiceImpl.class);

    @ConfigProperty(name = "file.upload.location")
    String fileUploadLocation;

    @Inject
    Vertx vertx;

    @Override
    public Uni<Void> deleteFileUpload(String fileName) {
        String uploadedFileName = fileUploadLocation + fileName;
        FileSystem fileSystem = vertx.fileSystem();
        Boolean exists = fileSystem.exists(uploadedFileName).map(existsResult -> existsResult).await().indefinitely();
        if (exists) {
            return fileSystem.delete(uploadedFileName);
        }
        return Uni.createFrom().item(null);
    }

    @Override
    public Uni<String> writeFileUpload(InputStream is) {
        String fileName = UUID.randomUUID().toString();

        String uploadedFileName = fileUploadLocation + fileName;
        FileSystem fileSystem = vertx.fileSystem();
        //return fileSystem.cop(uploadedFileName, is);
        return null;
    }
}
