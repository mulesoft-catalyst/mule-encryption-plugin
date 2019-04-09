/*
 * (c) 2003-2018 MuleSoft, Inc. This software is protected under international copyright
 * law. All use of this software is subject to MuleSoft's Master Subscription Agreement
 * (or other master license agreement) separately entered into in writing between you and
 * MuleSoft. If such an agreement is not in place, you may not use the software.
 */
package com.mulesoft.services.encryption;

import java.util.Properties;

public class PluginSettingsBuilder {
    private String keyStoreLocation;
    private String algorithm;
    private String blockMode;
    private String padding;
    private String keyStorePassword;
    private String keyAlias;
    private String keyPassword;
    private int corePoolSize;
    private int maxPoolSize;

    private Properties properties;

    public PluginSettingsBuilder fromProperties(Properties props) {
        keyStoreLocation = props.getProperty(SettingsKeys.KEYSTORE_LOCATION, "enc-keystore.jceks");
        algorithm = props.getProperty(SettingsKeys.ALGORITHM,"AES");
        blockMode = props.getProperty(SettingsKeys.BLOCK_MODE,"CBC");
        padding = props.getProperty(SettingsKeys.PADDING,"PKCS5Padding");
        keyStorePassword = props.getProperty(SettingsKeys.KEYSTORE_PASSWORD,"changeit");
        keyAlias = props.getProperty(SettingsKeys.KEY_ALIAS,"enc-key");
        keyPassword = props.getProperty(SettingsKeys.KEY_PASSWORD,"changeit");
        corePoolSize = Integer.parseInt(props.getProperty(SettingsKeys.CORE_POOL_SIZE, "16"));
        maxPoolSize = Integer.parseInt(props.getProperty(SettingsKeys.MAX_POOL_SIZE, "128"));

        return this;
    }

    public PluginSettingsBuilder setKeyStoreLocation(String keyStoreLocation) {
        this.keyStoreLocation = keyStoreLocation;
        return this;
    }

    public PluginSettingsBuilder setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
        return this;
    }

    public PluginSettingsBuilder setBlockMode(String blockMode) {
        this.blockMode = blockMode;
        return this;
    }

    public PluginSettingsBuilder setPadding(String padding) {
        this.padding = padding;
        return this;
    }

    public PluginSettingsBuilder setKeyStorePassword(String keyStorePassword) {
        this.keyStorePassword = keyStorePassword;
        return this;
    }

    public PluginSettingsBuilder setKeyAlias(String keyAlias) {
        this.keyAlias = keyAlias;
        return this;
    }

    public PluginSettingsBuilder setKeyPassword(String keyPassword) {
        this.keyPassword = keyPassword;
        return this;
    }

    public PluginSettings build() {
        return new PluginSettings(keyStoreLocation, algorithm, blockMode, padding, keyStorePassword, keyAlias, keyPassword, corePoolSize, maxPoolSize);
    }

    public PluginSettingsBuilder setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
        return this;
    }

    public PluginSettingsBuilder setMaxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
        return this;
    }
}