package io.m9rcy.playground.web.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentServiceRequest {

    private String fileId;
    private String fileName;
    private String fileType;
    private String description;
    private String location;
    private String referenceId;
    private String crsId;


}
