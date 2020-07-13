package io.m9rcy.playground.domain.model.service;

import io.m9rcy.playground.application.data.ApplicationData;
import io.m9rcy.playground.application.data.ApplicationsData;
import io.m9rcy.playground.application.data.DocumentData;

import java.util.List;

public interface ApplicationService {

    ApplicationsData findRecentApplications(Long loggedUserId, int offset, int limit);

    ApplicationsData findApplications(
            int offset,
            int limit,
            Long loggedUserId);

    ApplicationData create(
            String title, String description, String applicationName, Long customerId, String payload);

    ApplicationData findBySlug(String slug);

    ApplicationData update(String slug, String title, String description, String applicationName, Long customerId, String payload);

    void delete(String slug, Long authorId);

    List<DocumentData> findDocumentBySlug(String slug, Long loggedUserId);

    DocumentData createDocument(String slug, String body, Long commentAuthorId);

    void deleteDocument(String slug, Long commentId, Long loggedUserId);

}
