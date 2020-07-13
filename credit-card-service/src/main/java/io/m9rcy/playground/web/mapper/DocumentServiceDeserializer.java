package io.m9rcy.playground.web.mapper;

import io.m9rcy.playground.web.model.request.DocumentServiceRequest;
import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class DocumentServiceDeserializer extends ObjectMapperDeserializer<DocumentServiceRequest> {

    public DocumentServiceDeserializer(){
        super(DocumentServiceRequest.class);
    }
}
