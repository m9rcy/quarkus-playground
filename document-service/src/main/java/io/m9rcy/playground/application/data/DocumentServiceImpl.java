package io.m9rcy.playground.application.data;

import io.m9rcy.playground.domain.model.service.DocumentService;

import java.util.List;

public class DocumentServiceImpl implements DocumentService {

    @Override
    public DocumentsData findRecentArticles(Long loggedUserId, int offset, int limit) {
        return null;
    }

    @Override
    public DocumentsData findArticles(int offset, int limit, String crsId, List<String> tags) {
        return null;
    }

    @Override
    public DocumentData create(String fileName, String description, String contentType, List<String> tagList,
                               String crsId, String referenceId) {
        return null;
    }

    @Override
    public DocumentData update(String fileName, String description, String contentType, String crsId,
                               String referenceId) {
        return null;
    }

    @Override
    public void delete(String slug, Long authorId) {

    }

    @Override
    public DocumentData trashDocument(Long documentId, String crsId) {
        return null;
    }

    @Override
    public DocumentData unTrashDocument(Long documentId, String crsId) {
        return null;
    }
}
