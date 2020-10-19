package io.m9rcy.playground.domain.model.repository.panache;

import io.m9rcy.playground.domain.model.entity.Document;
import io.m9rcy.playground.domain.model.repository.DocumentRepository;
import io.m9rcy.playground.domain.model.repository.util.SimpleQueryBuilder;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Parameters;
import io.quarkus.panache.common.Sort;

import javax.enterprise.context.ApplicationScoped;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class DocumentRepositoryPanache implements PanacheRepository<Document>, DocumentRepository {
    @Override
    public List<Document> findDocuments(int offset, int limit, String crsId, List<String> tags) {
        Map<String, Object> params = new LinkedHashMap<>();
        SimpleQueryBuilder findDocumentsQueryBuilder = new SimpleQueryBuilder();
        findDocumentsQueryBuilder.addQueryStatement("select documents from Document as documents");
        configFilterFindDocumentsQueryBuilder(
                findDocumentsQueryBuilder, tags,  params);
        return find(findDocumentsQueryBuilder.toQueryString(),
                Sort.descending("createdAt").and("updatedAt").descending(),
                params)
                .page(Page.of(offset, limit))
                .list();    }


    @Override
    public Optional<Document> findByIdAndCrsId(Long id, String crsId) {
        return find("document.id = :documentId and crsId = :crsId",
                    Parameters.with("documentId",id).and("crsId", crsId)).firstResultOptional();
    }

    @Override
    public Document create(Document document) {
        persistAndFlush(document);
        return document;
    }

    @Override
    public void remove(Document document) {
        delete(document);
    }

    @Override
    public long count(List<String> tags) {
        Map<String, Object> params = new LinkedHashMap<>();
        SimpleQueryBuilder countDocumentsQueryBuilder = new SimpleQueryBuilder();
        countDocumentsQueryBuilder.addQueryStatement("from Document as documents");
        configFilterFindDocumentsQueryBuilder(
                countDocumentsQueryBuilder, tags, params);
        return count(countDocumentsQueryBuilder.toQueryString(), params);    }

    @Override
    public long count(String crsId) {
        return count(
                "from Document as documents where crsId = :crsId",
                Parameters.with("crsId", crsId));    }

    private void configFilterFindDocumentsQueryBuilder(SimpleQueryBuilder findDocumentsQueryBuilder, List<String> tags, Map<String, Object> params) {
        findDocumentsQueryBuilder.updateQueryStatementConditional(
                isNotEmpty(tags),
                "inner join documents.tags as tags inner join tags.primaryKey.tag as tag",
                "upper(tag.name) in (:tags)",
                () -> params.put("tags", toUpperCase(tags)));
    }

    private boolean isNotEmpty(List<?> list) {
        return list != null && !list.isEmpty();
    }

    private List<String> toUpperCase(List<String> tags) {
        return tags.stream().map(String::toUpperCase).collect(Collectors.toList());
    }
}
