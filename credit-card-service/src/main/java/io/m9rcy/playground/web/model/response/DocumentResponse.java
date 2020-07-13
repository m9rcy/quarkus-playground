package io.m9rcy.playground.web.model.response;

import io.m9rcy.playground.application.data.DocumentData;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@RegisterForReflection
public class DocumentResponse {

    private String fileId;
    private String contentType;
    private String description;
    private String location;
    private String customerId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public DocumentResponse(DocumentData documentData) {
        this.fileId = documentData.getFileId();
        this.contentType = documentData.getContentType();
        this.description = documentData.getDescription();
        this.location = documentData.getLocation();
        this.customerId = documentData.getApplication().getCustomerId();
        this.createdAt = documentData.getCreatedAt();
        this.updatedAt = documentData.getUpdatedAt();
    }
}
