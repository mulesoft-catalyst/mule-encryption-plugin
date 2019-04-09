/*
 * (c) 2003-2018 MuleSoft, Inc. This software is protected under international copyright
 * law. All use of this software is subject to MuleSoft's Master Subscription Agreement
 * (or other master license agreement) separately entered into in writing between you and
 * MuleSoft. If such an agreement is not in place, you may not use the software.
 */
package com.mulesoft.services.encryption;

import com.mulesoft.mule.plugin.SimpleMulePlugin;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.api.object.ObjectFactory;
import org.mule.config.PoolingProfile;
import org.mule.util.pool.CommonsPoolObjectPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;

public class MuleEncryptionPlugin extends SimpleMulePlugin {

    private static MuleEncryptionPlugin instance;

    private static final Logger logger = LoggerFactory.getLogger(MuleEncryptionPlugin.class);

    private CommonsPoolObjectPool pool;

    @Override
    public void initialise() throws InitialisationException {
        this.instance = this;

        logger.info("Initialising Encryption Plugin...");

        //do a test run
        PluginSettings settings = PluginSettings.builder()
                .fromProperties(System.getProperties())
                .build();

        PoolingProfile pp = new PoolingProfile(settings.getMaxPoolSize(),
                settings.getCorePoolSize(),
                PoolingProfile.DEFAULT_MAX_POOL_WAIT,
                PoolingProfile.DEFAULT_POOL_EXHAUSTED_ACTION,
                PoolingProfile.DEFAULT_POOL_INITIALISATION_POLICY);

        ObjectFactory of = new CipherObjectFactory(settings);

        pool = new CommonsPoolObjectPool(of, pp, null);

        pool.initialise();
    }

    @Override
    public void dispose() {
        logger.info("Disposing Encryption Plugin...");

        this.instance = null;
        pool.dispose();
    }

    public static Cipher getCipher() throws Exception {

        if (instance == null) {
            throw new IllegalStateException("Plugin not properly initialized");
        }

        return (Cipher) instance.pool.borrowObject();
    }

    public static void returnCipher(Cipher cipher) {
        if (instance == null) {
            throw new IllegalStateException("Plugin not properly initialized");
        }

        instance.pool.returnObject(cipher);
    }
}
