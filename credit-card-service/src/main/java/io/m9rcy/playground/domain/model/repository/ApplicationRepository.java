package io.m9rcy.playground.domain.model.repository;

import io.m9rcy.playground.domain.model.entity.Application;

import java.util.List;
import java.util.Optional;

public interface ApplicationRepository {

    List<Application> findApplications(
            int offset, int limit);

    Application create(Application application);

    boolean existsBySlug(String slug);

    Optional<Application> findBySlug(String slug);

    void remove(Application application);

    Optional<Application> findByIdAndSlug(Long applicationId, String slug);

    List<Application> findMostRecentApplications(Long loggedUserId, int offset, int limit);

    long count(Long loggedUserId);
}
