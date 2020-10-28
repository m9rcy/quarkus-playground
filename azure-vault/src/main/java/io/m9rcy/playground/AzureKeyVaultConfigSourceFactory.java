package io.m9rcy.playground;

import io.smallrye.config.ConfigSourceContext;
import io.smallrye.config.ConfigSourceFactory;
import org.eclipse.microprofile.config.spi.ConfigSource;

import java.util.Collections;

public class AzureKeyVaultConfigSourceFactory implements ConfigSourceFactory {

    @Override
    public ConfigSource getConfigSource(ConfigSourceContext configSourceContext) {
        return new AzureKeyVaultConfigSource();
    }
}
