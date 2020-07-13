package io.m9rcy.playground.web.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.m9rcy.playground.application.data.ApplicationData;
import io.m9rcy.playground.web.model.request.NewApplication;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@RegisterForReflection
public class ApplicationResponse {

    String customerId;
    String applicantName;
    NewApplication application;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime updatedAt;

    public ApplicationResponse(ApplicationData applicationData) {
        this.customerId = applicationData.getCustomerId();
        this.applicantName = applicationData.getApplicationName();
        this.createdAt = applicationData.getCreatedAt();
        this.updatedAt = applicationData.getUpdatedAt();
    }
}
