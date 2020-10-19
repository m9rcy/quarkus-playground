package io.m9rcy.playground.domain.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "DOCUMENTS_TAGS")
public class DocumentsTags {
    @EmbeddedId private DocumentsTagsKey primaryKey;

    public DocumentsTags(DocumentsTagsKey primaryKey) {
        this.primaryKey = primaryKey;
    }

    @ManyToOne
    @JoinColumn(insertable = false, updatable = false)
    private Document document;

    @ManyToOne
    @JoinColumn(insertable = false, updatable = false)
    private Tag tag;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        DocumentsTags that = (DocumentsTags) o;
        return Objects.equals(primaryKey, that.primaryKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(primaryKey);
    }
}