package io.m9rcy.playground.web.model.request;

import io.m9rcy.playground.domain.model.constants.DocumentType;
import io.m9rcy.playground.domain.model.constants.ValidationMessages;
import io.m9rcy.playground.web.validation.constraint.FieldMustBeValidEnumValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jboss.resteasy.annotations.providers.multipart.PartType;

import javax.validation.constraints.NotNull;
import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;
import java.io.InputStream;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentMultipartRequest {

    @NotNull(message = ValidationMessages.FILE_MUST_BE_NOT_NULL)
    @FormParam("file")
    @PartType(MediaType.APPLICATION_OCTET_STREAM)
    public InputStream file;

    @FormParam("fileName")
    @PartType(MediaType.TEXT_PLAIN)
    public String fileName;

    @FormParam("description")
    @PartType(MediaType.TEXT_PLAIN)
    public String description;

    @NotNull(message = ValidationMessages.DOCUMENT_TYPE_MUST_BE_NOT_BLANK)
    @FormParam("documentType")
    @PartType(MediaType.TEXT_PLAIN)
    @FieldMustBeValidEnumValue(
            enumClazz = DocumentType.class,
            message = ValidationMessages.DOCUMENT_TYPE_INVALID
    )
    public String documentType;



}
