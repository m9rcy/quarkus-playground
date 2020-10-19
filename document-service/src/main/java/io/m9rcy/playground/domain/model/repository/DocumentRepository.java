package io.m9rcy.playground.domain.model.repository;

import io.m9rcy.playground.domain.model.entity.Document;

import java.util.List;
import java.util.Optional;

public interface DocumentRepository {
  List<Document> findDocuments(
      int offset, int limit, String crsId, List<String> tags);

  Optional<Document> findByIdAndCrsId(Long id, String crsId);

  Document create(Document document);

  void remove(Document document);

  long count(List<String> tags);

  long count(String crsId);
}