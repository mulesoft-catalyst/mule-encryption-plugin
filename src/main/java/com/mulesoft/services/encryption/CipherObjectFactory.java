/*
 * (c) 2003-2018 MuleSoft, Inc. This software is protected under international copyright
 * law. All use of this software is subject to MuleSoft's Master Subscription Agreement
 * (or other master license agreement) separately entered into in writing between you and
 * MuleSoft. If such an agreement is not in place, you may not use the software.
 */
package com.mulesoft.services.encryption;

import org.mule.api.MuleContext;
import org.mule.api.lifecycle.InitialisationCallback;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.api.object.ObjectFactory;

import javax.crypto.Cipher;

public class CipherObjectFactory implements ObjectFactory {

    private final PluginSettings settings;

    public CipherObjectFactory(PluginSettings settings) {
        this.settings = settings;
    }

    @Override
    public Object getInstance(MuleContext muleContext) throws Exception {
        return CipherBuilder.builder()
                .withSettings(settings)
                .build();
    }

    @Override
    public Class<?> getObjectClass() {
        return Cipher.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    @Override
    public boolean isExternallyManagedLifecycle() {
        return false;
    }

    @Override
    public boolean isAutoWireObject() {
        return false;
    }

    @Override
    public void addObjectInitialisationCallback(InitialisationCallback callback) {

    }

    @Override
    public void dispose() {

    }

    @Override
    public void initialise() throws InitialisationException {

    }
}
