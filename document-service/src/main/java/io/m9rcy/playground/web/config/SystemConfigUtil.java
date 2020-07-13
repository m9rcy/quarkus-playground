package io.m9rcy.playground.web.config;

import org.eclipse.microprofile.config.ConfigProvider;

public class SystemConfigUtil {

    public static String getStringConfig(String config, String defaultValue) {
        String configValue = defaultValue;
        if (config != null) {
            configValue = ConfigProvider.getConfig().getValue(config, String.class);
        }

        return configValue;
    }
}
