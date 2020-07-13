package io.m9rcy.playground.application.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jboss.resteasy.annotations.providers.multipart.PartType;

import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentData {

    private String fileId;
    private String fileName;
    private String fileType;
    private String description;
    private String location;
    public String referenceId;
    public String crsId;


}
