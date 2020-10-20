package io.m9rcy.playground.domain.model.service;

import io.smallrye.mutiny.Uni;

import java.io.InputStream;

public interface FileService {

    Uni<Void> deleteFileUpload(String fileName);

    Uni<String> writeFileUpload(InputStream is);

}
