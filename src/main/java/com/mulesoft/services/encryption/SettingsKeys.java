/*
 * (c) 2003-2018 MuleSoft, Inc. This software is protected under international copyright
 * law. All use of this software is subject to MuleSoft's Master Subscription Agreement
 * (or other master license agreement) separately entered into in writing between you and
 * MuleSoft. If such an agreement is not in place, you may not use the software.
 */
package com.mulesoft.services.encryption;

public interface SettingsKeys {

    String PREFIX = "mule.encryption.plugin.";

    String KEYSTORE_LOCATION = PREFIX + "keystore.location";
    String KEYSTORE_PASSWORD = PREFIX + "keystore.password";
    String KEY_ALIAS = PREFIX + "key.alias";
    String KEY_PASSWORD = PREFIX + "key.password";
    String ALGORITHM = PREFIX + "encryption.algorithm";
    String BLOCK_MODE = PREFIX + "encryption.blockMode";
    String PADDING = PREFIX + "encryption.padding";
    String CORE_POOL_SIZE = PREFIX + "cipher.corePoolSize";
    String MAX_POOL_SIZE = PREFIX + "cipher.maxPoolSize";

}
