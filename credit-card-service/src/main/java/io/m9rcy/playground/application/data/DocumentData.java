package io.m9rcy.playground.application.data;

import io.m9rcy.playground.domain.model.entity.Application;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@RegisterForReflection
public class DocumentData {
    private String fileId;
    private String contentType;
    private String description;
    private String location;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Application application;
}
