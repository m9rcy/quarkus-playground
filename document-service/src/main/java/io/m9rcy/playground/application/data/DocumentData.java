package io.m9rcy.playground.application.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentData {

    private String fileId;
    private String fileName;
    private String contentType;
    private String description;
    private String location;
    private String referenceId;
    private String crsId;
    private boolean deleted;
    private List<String> tagList;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;




}
