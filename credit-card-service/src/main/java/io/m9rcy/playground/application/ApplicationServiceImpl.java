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
import org.eclipse.microprofile.opentracing.Traced;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
@Traced
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
    @Transactional
    public ApplicationsData findRecentApplications(Long loggedUserId, int offset, int limit) {
        return null;
    }

    @Override
    @Transactional
    public ApplicationsData findApplications(int offset, int limit, Long loggedUserId) {
        return null;
    }

    @Override
    @Transactional
    public ApplicationData create(String title, String description, String applicationName, Long customerId, String payload) {
        Application application = createApplication(title, description, applicationName, customerId, payload);
        return new ApplicationData(application.getSlug(), application.getTitle(), application.getDescription(), application.getApplicationName(), application.getCustomerId(), application.getPayload(), application.isActive(), application.getCreatedAt(), application.getUpdatedAt());
    }

    @Override
    @Transactional
    public ApplicationData findBySlug(String slug) {

        Application application = applicationRepository.findBySlug(slug).orElseThrow(ApplicationNotFoundException::new);

        return getApplication(application);
    }

    @Override
    @Transactional
    public ApplicationData update(String slug, String title, String description, String applicationName, Long customerId, String payload) {
        return null;
    }

    @Override
    @Transactional
    public void delete(String slug, Long authorId) {

    }

    @Override
    @Transactional
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
        return new ApplicationData(application.getSlug(), application.getTitle(), application.getDescription(), application.getApplicationName(), application.getCustomerId(), application.getPayload(), application.isActive(), application.getCreatedAt(), application.getUpdatedAt());
    }

    private Application createApplication(String title, String description, String applicationName, Long customerId, String payload) {
        Application application = new Application();
        configSlug(title, application);
        application.setDescription(description);
        application.setApplicationName(applicationName);
        //FIXME
        application.setCustomerId(String.valueOf(customerId));
        application.setPayload(payload);
        application.setActive(true);

        return applicationRepository.create(application);
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
