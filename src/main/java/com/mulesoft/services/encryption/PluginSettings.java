/*
 * (c) 2003-2018 MuleSoft, Inc. This software is protected under international copyright
 * law. All use of this software is subject to MuleSoft's Master Subscription Agreement
 * (or other master license agreement) separately entered into in writing between you and
 * MuleSoft. If such an agreement is not in place, you may not use the software.
 */
package com.mulesoft.services.encryption;

public class PluginSettings {

    private final String keyStoreLocation;
    private final String algorithm;
    private final String blockMode;
    private final String padding;
    private final String keyStorePassword;
    private final String keyAlias;
    private final String keyPassword;
    private final int corePoolSize;
    private final int maxPoolSize;

    public static PluginSettingsBuilder builder() {
        return new PluginSettingsBuilder();
    }

    public PluginSettings(String keyStoreLocation, String algorithm, String blockMode, String padding, String keyStorePassword, String keyAlias, String keyPassword, int corePoolSize, int maxPoolSize) {
        this.keyStoreLocation = keyStoreLocation;
        this.algorithm = algorithm;
        this.blockMode = blockMode;
        this.padding = padding;
        this.keyStorePassword = keyStorePassword;
        this.keyAlias = keyAlias;
        this.keyPassword = keyPassword;
        this.corePoolSize = corePoolSize;
        this.maxPoolSize = maxPoolSize;
    }

    public String getKeyStoreLocation() {
        return keyStoreLocation;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public String getBlockMode() {
        return blockMode;
    }

    public String getPadding() {
        return padding;
    }

    public String getKeyStorePassword() {
        return keyStorePassword;
    }

    public String getKeyAlias() {
        return keyAlias;
    }

    public String getKeyPassword() {
        return keyPassword;
    }

    public int getCorePoolSize() {
        return corePoolSize;
    }

    public int getMaxPoolSize() {
        return maxPoolSize;
    }
}
