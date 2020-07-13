package io.m9rcy.playground.application.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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



}
