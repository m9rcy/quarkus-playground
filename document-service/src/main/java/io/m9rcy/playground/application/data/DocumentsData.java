package io.m9rcy.playground.application.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentsData {

    List<DocumentData> documents;
    Long documentCount;


}
