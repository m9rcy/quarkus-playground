package io.m9rcy.playground.web.model.response;

import io.m9rcy.playground.application.data.ApplicationsData;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@RegisterForReflection
public class ApplicationsResponse {

    List<ApplicationResponse> applications;
    long applicationCount;

    public ApplicationsResponse(ApplicationsData applicationsData) {
        this.applications = applicationsData.getApplications().stream().map(ApplicationResponse::new).collect(Collectors.toList());
        this.applicationCount = applicationsData.getApplicationsCount();
    }
}
