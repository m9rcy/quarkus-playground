package io.m9rcy.playground.web.model.response;

import io.m9rcy.playground.application.data.DocumentData;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@RegisterForReflection
public class DocumentResponse {

    String fileId;
    String fileName;
    String contentType;
    String description;
    String location;
    String referenceId;
    String crsId;
    List<String> tagList;

    public DocumentResponse(DocumentData documentData) {
        this.fileId = documentData.getFileId();
        this.fileName = documentData.getFileName();
        this.contentType = documentData.getContentType();
        this.description = documentData.getDescription();
        this.location = documentData.getLocation();
        this.referenceId = documentData.getReferenceId();
        this.crsId = documentData.getCrsId();
        this.tagList = documentData.getTagList();
    }
}
