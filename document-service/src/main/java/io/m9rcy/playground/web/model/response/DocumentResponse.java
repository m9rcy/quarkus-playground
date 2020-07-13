package io.m9rcy.playground.web.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentResponse {

    String fileId;
    String fileName;
    String contentType;
    Long contentSize;
    String location;
    String documentType;

}