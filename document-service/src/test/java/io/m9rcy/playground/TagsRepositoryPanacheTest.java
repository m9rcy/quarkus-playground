package io.m9rcy.playground;

import io.m9rcy.playground.domain.model.entity.Tag;
import io.m9rcy.playground.domain.model.repository.TagRepository;
import io.m9rcy.playground.domain.model.repository.panache.TagsRepositoryPanache;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@QuarkusTest
@Transactional
@QuarkusTestResource(H2DatabaseTestResource.class)
class TagsRepositoryPanacheTest {

    @Inject
    private TagsRepositoryPanache tagRepository;

    @BeforeEach
    void clean() {
        tagRepository.deleteAll();
    }

    @Test
    void shouldFindTagByName() {
        Tag tag = createTag("credit-card");
        Assertions.assertSame(tag,tagRepository.findByName("credit-card")
                .get());
        Assertions.assertFalse(tagRepository.findByName("personal-loan")
                .isPresent());
    }

    @Test
    void shouldCreateATag() {
        Tag tag = createTag("credit-card");
        Assertions.assertNotNull(tag.getId(),"Id should not be null");
    }

    @Test
    void shouldReturnAllTags() {
        Tag tag = createTag("credit-card");
        tag = createTag("home-loan");

        List<Tag> allTags = tagRepository.findAllTags();
        Assertions.assertTrue(allTags.size() >= 1);
        Assertions.assertTrue(allTags.contains(tag));
    }

    //Mock and do the actual in IT
    @Test
    void shouldReturnAllTagsOfADocument() {
        TagRepository mock = Mockito.mock(TagRepository.class);
        List<Tag> tags = Arrays.stream(new Tag[]{new Tag("credit-card"),new Tag("personal-loan")})
                .collect(Collectors.toList());
        Mockito.when(mock.findDocumentTags(1L))
                .thenReturn(tags);
        Assertions.assertSame(2,mock.findDocumentTags(1L)
                .size());

    }

    private Tag createTag(String name) {
        Tag tag = new Tag(name);
        tag = tagRepository.create(tag);
        return tag;
    }

}