package io.m9rcy.playground.domain.model.repository.panache;

import io.m9rcy.playground.domain.model.entity.Document;
import io.m9rcy.playground.domain.model.repository.DocumentRepository;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class DocumentRepositoryPanache implements PanacheRepository<Document>, DocumentRepository {
    @Override
    public Document create(Document document) {
        persistAndFlush(document);
        return document;
    }

    @Override
    public Optional<Document> findDocument(String slug, Long documentId, Long customerId) {
        return Optional.empty();
    }

    @Override
    public void remove(Document document) {
        delete(document);
    }

    @Override
    public List<Document> findApplicationDocuments(Long applicationId) {
        return null;
    }
}
