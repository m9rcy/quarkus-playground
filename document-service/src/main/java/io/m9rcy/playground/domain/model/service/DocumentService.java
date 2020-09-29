package io.m9rcy.playground.domain.model.service;

import io.m9rcy.playground.application.data.DocumentData;
import io.m9rcy.playground.application.data.DocumentsData;

import java.util.List;

public interface DocumentService {

    DocumentsData findRecentArticles(Long loggedUserId, int offset, int limit);

    DocumentsData findArticles(
            int offset,
            int limit,
            String crsId,
            List<String> tags);

    DocumentData create(
            String fileName, String description, String contentType, List<String> tagList, String crsId, String referenceId);

    DocumentData update(String fileName, String description, String contentType, String crsId, String referenceId);

    void delete(String slug, Long authorId);

    DocumentData trashDocument(Long documentId, String crsId);

    DocumentData unTrashDocument(Long documentId, String crsId);

}
