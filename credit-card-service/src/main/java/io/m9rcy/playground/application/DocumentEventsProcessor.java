package io.m9rcy.playground.application;

import io.m9rcy.playground.application.data.ApplicationData;
import io.m9rcy.playground.domain.model.entity.Application;
import io.m9rcy.playground.domain.model.entity.Document;
import io.m9rcy.playground.domain.model.repository.ApplicationRepository;
import io.m9rcy.playground.domain.model.repository.DocumentRepository;
import io.m9rcy.playground.domain.model.service.ApplicationService;
import io.m9rcy.playground.web.model.request.DocumentServiceRequest;
import io.m9rcy.playground.web.resource.CreditCardResource;
import io.smallrye.reactive.messaging.annotations.Blocking;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Optional;

@ApplicationScoped
public class DocumentEventsProcessor {

    Class clazz;
    private static final Logger LOG = LoggerFactory.getLogger(DocumentEventsProcessor.class);

    @Inject
    private ApplicationRepository applicationRepository;

    //FIXME
    @Inject
    private DocumentRepository documentRepository;

    @Incoming("document-events")
    @Blocking
    public void process(DocumentServiceRequest request) {
        LOG.info("Hello");
        LOG.info("Order {} for {} is ready", request.getFileId(), request.getDescription());

//        Optional<Application> application = applicationRepository.findBySlug("johnsmithjohn-johncc");
//        Document document = new Document();
//        document.setContentType(request.getFileType());
//        document.setDescription(request.getDescription());
//        document.setFileId(request.getFileId());
//        document.setLocation(request.getLocation());
//        document.setApplication(application.get());
//        document =  documentRepository.create(document);
        //LOG.info("Order {} for {} is ready", document.getId(), application.get().getSlug());

    }
}
