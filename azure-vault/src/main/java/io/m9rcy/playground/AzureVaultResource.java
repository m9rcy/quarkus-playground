package io.m9rcy.playground;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import static javax.ws.rs.core.MediaType.TEXT_HTML;

@Path("/azure-vault/demo")
public class AzureVaultResource {

    @Inject
    org.eclipse.microprofile.config.Config config;

    @Inject
    @ConfigProperty(name = "demo-key", defaultValue = "Unknown")
    String keyValue;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "demo-key vault value is" + keyValue;
    }

    @GET
    @Path("config")
    @Produces(TEXT_HTML)
    public String info() {
        return "Welcome to the server! I know that the value for the key 'demo-key' is: '" + keyValue
                + "'<br/><br/>"
                + "By the way, I can also look it up in a non-DI fashion: '" + config.getValue("demo-key", String.class) + "'";
    }
}