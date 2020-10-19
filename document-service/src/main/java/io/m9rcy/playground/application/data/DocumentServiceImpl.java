package io.m9rcy.playground.application.data;

import io.m9rcy.playground.domain.model.entity.Document;
import io.m9rcy.playground.domain.model.entity.DocumentsTags;
import io.m9rcy.playground.domain.model.entity.DocumentsTagsKey;
import io.m9rcy.playground.domain.model.entity.Tag;
import io.m9rcy.playground.domain.model.exception.DocumentNotFoundException;
import io.m9rcy.playground.domain.model.repository.DocumentRepository;
import io.m9rcy.playground.domain.model.repository.DocumentsTagsRepository;
import io.m9rcy.playground.domain.model.repository.TagRepository;
import io.m9rcy.playground.domain.model.service.DocumentService;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class DocumentServiceImpl implements DocumentService {

    private static final int DEFAULT_LIMIT = 20;

    DocumentRepository documentRepository;
    DocumentsTagsRepository documentsTagsRepository;
    TagRepository tagRepository;

    public DocumentServiceImpl(DocumentRepository documentRepository,
                               DocumentsTagsRepository documentsTagsRepository,
                               TagRepository tagsRepository) {
        this.documentRepository = documentRepository;
        this.documentsTagsRepository = documentsTagsRepository;
        this.tagRepository = tagsRepository;
    }


    @Override
    public DocumentsData findRecentDocuments(String crsId, int offset, int limit) {
        return null;
    }

    @Override
    public DocumentsData findDocuments(int offset, int limit, String crsId, List<String> tags) {
        List<Document> documents = documentRepository.findDocuments(offset,limit, crsId, tags);
        long documentsCount = documentRepository.count(crsId);
        return new DocumentsData(toResultList(documents,crsId),documentsCount);
    }

    @Override
    @Transactional
    public DocumentData create(String fileName, String description, String contentType, List<String> tagList,
                               String crsId, String referenceId) {
        Document document = new Document();
        document.setFileName(fileName);
        document.setDescription(description);
        document.setContentType(contentType);
        document.setCrsId(crsId);
        document.setReferenceId(referenceId);

        document = documentRepository.create(document);

        createDocumentsTags(document, tagList);
        return getDocument(document, crsId);
    }

    @Override
    public DocumentData update(String fileName, String description, String contentType, String crsId,
                               String referenceId) {
        return null;
    }

    @Override
    public void delete(Long documentId, String crsId) {
        Document document = documentRepository.findByIdAndCrsId(documentId, crsId).orElseThrow(DocumentNotFoundException::new);
        documentRepository.remove(document);
    }

    @Override
    public DocumentData trashDocument(Long documentId, String crsId) {
        Document document = documentRepository.findByIdAndCrsId(documentId, crsId).orElseThrow(DocumentNotFoundException::new);
        document.setDeleted(true);
        return getDocument(document, crsId);
    }

    @Override
    public DocumentData unTrashDocument(Long documentId, String crsId) {
        Document document = documentRepository.findByIdAndCrsId(documentId, crsId).orElseThrow(DocumentNotFoundException::new);
        document.setDeleted(false);
        return getDocument(document, crsId);    }

    private void createDocumentsTags(Document document, List<String> tagList) {
        tagList.forEach(
                tagName -> {
                    Optional<Tag> tagOptional = tagRepository.findByName(tagName);

                    Tag tag = tagOptional.orElseGet(() -> createTag(tagName));

                    DocumentsTags documentsTags = createDocumentsTags(document, tag);
                    documentsTagsRepository.create(documentsTags);
                });
    }

    private Tag createTag(String tagName) {
        return tagRepository.create(new Tag(tagName));
    }

    private DocumentsTags createDocumentsTags(Document document, Tag tag) {
        DocumentsTagsKey documentsTagsKey = new DocumentsTagsKey(document, tag);
        return new DocumentsTags(documentsTagsKey);
    }

    private List<DocumentData> toResultList(List<Document> documents, String crsId) {
        return documents.stream()
                       .map(document -> getDocument(document, crsId))
                       .collect(Collectors.toList());
    }

    private DocumentData getDocument(Document document, String crsId) {
        List<String> tags = tagRepository.findDocumentTags(document.getId()).stream().map(Tag::getName).collect(Collectors.toList());

        return DocumentData.builder().fileId(document.getId().toString())
                .contentType(document.getContentType())
                .fileName(document.getFileName())
                .description(document.getDescription())
                .referenceId(document.getReferenceId())
                .location(null)
                .crsId(document.getCrsId())
                .deleted(false)
                .createdAt(document.getCreatedAt())
                .updatedAt(document.getUpdatedAt())
                .tagList(Collections.unmodifiableList(tags))
                .build();
    }

    private int getLimit(int limit) {
        return limit > 0 ? limit : DEFAULT_LIMIT;
    }

    private boolean isPresent(String value) {
        return value != null && !value.isEmpty();
    }
}
