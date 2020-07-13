package io.m9rcy.playground.web.model.response;

import io.m9rcy.playground.application.data.DocumentsData;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@RegisterForReflection
public class DocumentsResponse {
    List<DocumentResponse> documents;
    long documentCount;

    public DocumentsResponse(DocumentsData documentsData) {
        this.documents = documentsData.getDocuments().stream().map(DocumentResponse::new).collect(Collectors.toList());
        this.documentCount = documentsData.getDocumentsCount();
    }
}
