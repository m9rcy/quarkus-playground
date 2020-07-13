package io.m9rcy.playground.domain.model.service;

import io.m9rcy.playground.application.data.DocumentData;
import io.m9rcy.playground.application.data.DocumentsData;

public interface DocumentService {

    DocumentData uploadDocument();

    DocumentData downloadDocument();

    DocumentsData listAllDocuments();



}
