package io.m9rcy.playground.domain.model.repository;

import io.m9rcy.playground.domain.model.entity.Document;

import java.util.List;
import java.util.Optional;

public interface DocumentRepository {
    Document create(Document document);

    Optional<Document> findDocument(String slug, Long documentId, Long applicantId);

    void remove(Document document);

    List<Document> findApplicationDocuments(Long applicationId);
}
