package io.m9rcy.playground.domain.model.repository.panache;

import io.m9rcy.playground.domain.model.entity.Tag;
import io.m9rcy.playground.domain.model.repository.TagRepository;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;

import static io.quarkus.panache.common.Parameters.with;

@ApplicationScoped
public class TagsRepositoryPanache implements PanacheRepository<Tag>, TagRepository {
    @Override
    public Optional<Tag> findByName(String tagName) {
        return find("upper(name)", tagName.toUpperCase().trim()).firstResultOptional();
    }

    @Override
    public Tag create(Tag tag) {
        persistAndFlush(tag);
        return tag;
    }

    @Override
    public List<Tag> findAllTags() {
        return listAll();
    }

    @Override
    public List<Tag> findDocumentTags(Long documentId) {
        return find(
                "select tags from Tag as tags inner join tags.documentsTags as documentsTags where documentsTags.document.id = :documentId",
                with("documentId", documentId))
                .list();
    }
}
