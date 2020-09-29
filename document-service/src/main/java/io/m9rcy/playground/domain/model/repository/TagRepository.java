package io.m9rcy.playground.domain.model.repository;

import io.m9rcy.playground.domain.model.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface TagRepository {
  Optional<Tag> findByName(String tagName);

  Tag create(Tag tag);

  List<Tag> findAllTags();

  List<Tag> findDocumentTags(Long documentId);
}