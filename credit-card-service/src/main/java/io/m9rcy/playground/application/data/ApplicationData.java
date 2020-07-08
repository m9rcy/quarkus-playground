package io.m9rcy.playground.application.data;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@RegisterForReflection
public class ApplicationData {
    private String slug;
    private String title;
    private String description;
    private String applicationName;
    private String applicationId;

    private String payload;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
