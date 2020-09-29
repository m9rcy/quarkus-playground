package io.m9rcy.playground.domain.model.entity;

import lombok.AllArgsConstructor;
        import lombok.Getter;
        import lombok.NoArgsConstructor;
        import lombok.Setter;

        import javax.persistence.Embeddable;
        import javax.persistence.ManyToOne;
        import java.io.Serializable;
        import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class DocumentsTagsKey implements Serializable {

    @ManyToOne private Document document;
    @ManyToOne private Tag tag;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        DocumentsTagsKey that = (DocumentsTagsKey) o;
        return Objects.equals(document, that.document) && Objects.equals(tag, that.tag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(document, tag);
    }
}