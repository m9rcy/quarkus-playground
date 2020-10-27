package io.m9rcy.playground;

import com.microsoft.azure.keyvault.KeyVaultClient;
import com.microsoft.azure.keyvault.authentication.KeyVaultCredentials;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.config.spi.ConfigSource;
import io.smallrye.config.common.AbstractConfigSource;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Logger;
import java.util.stream.StreamSupport;

public class AzureKeyVaultConfigSource extends AbstractConfigSource {

    private final static Logger logger = Logger.getLogger(AzureKeyVaultConfigSource.class.getName());

    static AtomicReference <Optional <AzureKeyVaultOperation>> keyVaultOperation =
            new AtomicReference<>(Optional.empty());

    private static final String IGNORED_PREFIX = "azure.keyvault";
    private static final String KEYVAULT_CLIENT_ID = "azure.keyvault.client.id";
    private static final String KEYVAULT_CLIENT_KEY = "azure.keyvault.client.key";
    private static final String KEYVAULT_URL = "azure.keyvault.url";
    private static final String AZURE_KEY_VAULT_CONFIG_SOURCE_NAME = "azure.configsource.keyvault";

    private Optional<Config> config = Optional.empty();

    public AzureKeyVaultConfigSource() {
        super(AZURE_KEY_VAULT_CONFIG_SOURCE_NAME, 150);
    }

    @Override
    public Set<String> getPropertyNames() {
        return getClient().map(AzureKeyVaultOperation::getKeys)
                          .orElse(config
                                          .map(cnf -> StreamSupport.stream(cnf.getConfigSources().spliterator(), false)
                                                                   .filter(src -> src.getName() != this.getName()).map(ConfigSource::getPropertyNames)
                                                                   .filter(v -> v != null).findFirst().orElse(Collections.<String>emptySet()))
                                          .orElse(Collections.<String>emptySet()));
    }

    @Override
    public Map<String, String> getProperties() {
        return getClient().map(AzureKeyVaultOperation::getProperties)
                          .orElse(config
                                          .map(cnf -> StreamSupport.stream(cnf.getConfigSources().spliterator(), false)
                                                                   .filter(src -> src.getName() != this.getName()).map(ConfigSource::getProperties)
                                                                   .filter(v -> v != null).findFirst().orElse(Collections.<String, String>emptyMap()))
                                          .orElse(Collections.<String, String>emptyMap()));
    }

    @Override
    public String getValue(String key) {
        if (key.contains(IGNORED_PREFIX)) {
            logger.fine("ignored key->" + key);
            return null;
        }

        return getClient().map(keyVault -> keyVault.getValue(key))
                          .orElse(config.map(cnf -> StreamSupport.stream(cnf.getConfigSources().spliterator(), false)
                                                                 .filter(src -> src.getName() != this.getName()).map(src -> src.getValue(key))
                                                                 .filter(v -> v != null).findFirst().orElse(null)).orElse(null));
    }

    private Optional<AzureKeyVaultOperation> getClient() {
        Optional<AzureKeyVaultOperation> client = keyVaultOperation.get();

        if (!client.isPresent()) {
            logger.fine("Start Init");
            try {
                this.config = Optional.of(ConfigProvider.getConfig());
                Optional<String> keyvaultClientID =
                        config.get().getOptionalValue(KEYVAULT_CLIENT_ID, String.class);
                Optional<String> keyvaultClientKey =
                        config.get().getOptionalValue(KEYVAULT_CLIENT_KEY, String.class);
                Optional<String> keyvaultURL = config.get().getOptionalValue(KEYVAULT_URL, String.class);

                if (keyvaultClientID.isPresent() && keyvaultClientKey.isPresent()
                        && keyvaultURL.isPresent()) {
                    logger.info("Create KeyVault Client");
                    // create the keyvault client
                    KeyVaultCredentials credentials =
                            new AzureKeyVaultCredential(keyvaultClientID.get(), keyvaultClientKey.get());
                    KeyVaultClient keyVaultClient = new KeyVaultClient(credentials);
                    client = Optional.of(new AzureKeyVaultOperation(keyVaultClient, keyvaultURL.get()));
                    if (!keyVaultOperation.compareAndSet(Optional.empty(), client)) {
                        logger.warning("Client Instance Not Set");
                    }
                }
            } catch (Exception e) {
                logger.warning("Not Use Azure KeyVault");
            }
        }

        return client;
    }
}
