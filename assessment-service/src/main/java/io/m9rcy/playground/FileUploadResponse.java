package io.m9rcy.playground;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@RegisterForReflection
public class FileUploadResponse {

    String fileId;
    String fileName;
    String contentType;
    String description;
    String location;
    LocalDateTime fileReceivedAt;

}
