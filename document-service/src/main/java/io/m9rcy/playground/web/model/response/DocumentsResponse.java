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
    private List<DocumentResponse> documents;
    private long documentCount;

    public DocumentsResponse(DocumentsData result) {
        this.documents = result.getDocuments().stream().map(DocumentResponse::new).collect(Collectors.toList());
        this.documentCount = result.getDocumentCount();
    }
}
