package io.m9rcy.playground.domain.model.repository.panache;

import io.m9rcy.playground.domain.model.entity.Application;
import io.m9rcy.playground.domain.model.repository.ApplicationRepository;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ApplicationRepositoryPanache implements PanacheRepository<Application>, ApplicationRepository {
    @Override
    public List<Application> findApplications(int offset, int limit) {
        return null;
    }

    @Override
    public Application create(Application application) {
        persistAndFlush(application);
        return application;
    }

    @Override
    public boolean existsBySlug(String slug) {
        return count("upper(slug)", slug.toUpperCase().trim()) > 0;
    }

    @Override
    public Optional<Application> findBySlug(String slug) {
        return find("upper(slug)", slug.toUpperCase().trim()).firstResultOptional();
    }

    @Override
    public void remove(Application application) {
        delete(application);
    }

    @Override
    public Optional<Application> findByIdAndSlug(Long applicationId, String slug) {
        return Optional.empty();
    }

    @Override
    public List<Application> findMostRecentApplications(Long loggedUserId, int offset, int limit) {
        return null;
    }

    @Override
    public long count(Long loggedUserId) {
        return 0;
    }
}
