package io.m9rcy.playground.web.model.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.m9rcy.playground.domain.model.constants.DocumentType;
import io.m9rcy.playground.domain.model.constants.ValidationMessages;
import io.m9rcy.playground.web.validation.constraint.FieldMustBeValidEnumValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jboss.resteasy.annotations.providers.multipart.PartType;

import javax.inject.Inject;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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

    @NotNull(message = ValidationMessages.REFERENCE_ID_MUST_BE_NOT_NULL)
    @FormParam("referenceId")
    @PartType(MediaType.TEXT_PLAIN)
    public String referenceId;

    @FormParam("crsId")
    @PartType(MediaType.TEXT_PLAIN)
    public String crsId;

    @FormParam("tags")
    @NotNull(message = ValidationMessages.TAGS_MIN_VALUE)
    @Size(message = ValidationMessages.TAGS_CONTENT_INVALID, min = 1, max = 10)
    public ListWrapper<String> tags;

    public static class ListWrapper<T> extends ArrayList<T> {

        public static ListWrapper valueOf(String json) {
            ObjectMapper mapper = new ObjectMapper();
            ListWrapper obj = null;
            try {
                obj = mapper.readValue(json, ListWrapper.class);
            } catch (Exception e) {
                obj = new ListWrapper();
            }
            return obj;
        }
    }

}
