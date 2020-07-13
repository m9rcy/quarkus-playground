package io.m9rcy.playground;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.servers.Server;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/")
@OpenAPIDefinition(
        info = @Info(title = "Document Service API",
                description = "Internal API that provides Document services",
                version = "1.0",
                contact = @Contact(name = "m9rcy", url = "https://m9rcy.github.io")),
        servers = {
                @Server(url = "http://localhost:8090")
        },
        tags = {
                @Tag(name = "super-squad", description = "Maintained by Super Squad"),
                @Tag(name = "documents", description = "Document Services")
        }
)
public class DocumentApplication extends Application {
}
