package io.m9rcy.playground.domain.model.repository;

import io.m9rcy.playground.domain.model.entity.Document;

import java.util.List;

public interface DocumentRepository {
  List<Document> findDocuments(
      int offset, int limit, List<String> tags);

  Document create(Document document);

  void remove(Document document);

  long count(List<String> tags);

  long count(String crsId);
}