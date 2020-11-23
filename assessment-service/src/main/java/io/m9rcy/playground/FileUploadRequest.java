package io.m9rcy.playground;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@RegisterForReflection
public class FileUploadRequest {

    @NotNull
    private String fileName;
    @NotNull
    private String fileType;
    @NotNull
    private String fileContents;
    @NotNull
    private String referenceId;
    @NotNull
    private String tag;

}
