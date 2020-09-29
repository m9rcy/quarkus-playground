package io.m9rcy.playground.domain.model.service;

import io.m9rcy.playground.domain.model.entity.Tag;

import java.util.List;

public interface TagsService {
  List<Tag> findTags();
}