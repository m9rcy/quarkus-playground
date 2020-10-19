package io.m9rcy.playground.domain.model.repository.panache;

import io.m9rcy.playground.domain.model.entity.DocumentsTags;
import io.m9rcy.playground.domain.model.repository.DocumentsTagsRepository;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DocumentsTagsRepositoryPanache implements PanacheRepository<DocumentsTags>, DocumentsTagsRepository {
    @Override
    public DocumentsTags create(DocumentsTags documentsTags) {
        persistAndFlush(documentsTags);
        return documentsTags;
    }
}
