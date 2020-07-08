package io.m9rcy.playground.application;

import io.m9rcy.playground.application.data.ApplicationData;
import io.m9rcy.playground.application.data.ApplicationsData;
import io.m9rcy.playground.application.data.DocumentData;
import io.m9rcy.playground.domain.model.entity.Application;
import io.m9rcy.playground.domain.model.exception.ApplicationNotFoundException;
import io.m9rcy.playground.domain.model.provider.SlugProvider;
import io.m9rcy.playground.domain.model.repository.ApplicationRepository;
import io.m9rcy.playground.domain.model.repository.DocumentRepository;
import io.m9rcy.playground.domain.model.service.ApplicationService;

import java.util.List;
import java.util.UUID;

public class ApplicationServiceImpl implements ApplicationService {

    private static final int DEFAULT_LIMIT = 20;

    private ApplicationRepository applicationRepository;
    private DocumentRepository documentRepository;
    private SlugProvider slugProvider;

    public ApplicationServiceImpl(ApplicationRepository applicationRepository, DocumentRepository documentRepository, SlugProvider slugProvider) {
        this.applicationRepository = applicationRepository;
        this.documentRepository = documentRepository;
        this.slugProvider = slugProvider;
    }

    @Override
    public ApplicationsData findRecentApplications(Long loggedUserId, int offset, int limit) {
        return null;
    }

    @Override
    public ApplicationsData findApplications(int offset, int limit, Long loggedUserId) {
        return null;
    }

    @Override
    public ApplicationData create(String title, String description, String body, List<String> tagList, Long authorId) {
        return null;
    }

    @Override
    public ApplicationData findBySlug(String slug) {

        Application application = applicationRepository.findBySlug(slug).orElseThrow(ApplicationNotFoundException::new);

        return getApplication(application);
    }

    @Override
    public ApplicationData update(String slug, String title, String description, String body, Long authorId) {
        return null;
    }

    @Override
    public void delete(String slug, Long authorId) {

    }

    @Override
    public List<DocumentData> findDocumentBySlug(String slug, Long loggedUserId) {
        return null;
    }

    @Override
    public DocumentData createDocument(String slug, String body, Long commentAuthorId) {
        return null;
    }

    @Override
    public void deleteDocument(String slug, Long commentId, Long loggedUserId) {

    }

    private ApplicationData getApplication(Application application) {
        return new ApplicationData(application.getSlug(), application.getTitle(), application.getDescription(), application.getApplicationName(), application.getApplicationId(), application.getPayload(), application.isActive(), application.getCreatedAt(), application.getUpdatedAt());
    }

    private void configSlug(String title, Application application) {
        String slug = slugProvider.slugify(title);
        if (applicationRepository.existsBySlug(slug)) {
            slug += UUID
                    .randomUUID().toString();
        }
        application.setSlug(slug);
    }

    private int getLimit(int limit) {
        return limit > 0 ? limit : DEFAULT_LIMIT;
    }

    private boolean isPresent(String value) {
        return value != null && !value.isEmpty();
    }
}
