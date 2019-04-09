/*
 * (c) 2003-2018 MuleSoft, Inc. This software is protected under international copyright
 * law. All use of this software is subject to MuleSoft's Master Subscription Agreement
 * (or other master license agreement) separately entered into in writing between you and
 * MuleSoft. If such an agreement is not in place, you may not use the software.
 */
package com.mulesoft.services.encryption;

import org.mule.api.object.ObjectFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyStore;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CipherBuilder {

    private PluginSettings settings;

    public static final Logger logger = LoggerFactory.getLogger(MuleEncryptionPlugin.class);

    private static final Set<String> IV_OP_MODES = new HashSet<>(Arrays.asList("CBC", "CFB", "OFB", "PCBC"));

    private int cipherMode = Cipher.DECRYPT_MODE;

    public static CipherBuilder builder() {
        return new CipherBuilder();
    }

    public CipherBuilder withSettings(PluginSettings sets) {
        settings = sets;
        return this;
    }

    public CipherBuilder withCipherOperationMode(int cipherMode) {
        this.cipherMode = cipherMode;
        return this;
    }

    public Cipher build() {
        try {
            return doBuild(cipherMode);
        } catch (Exception ex) {
            logger.error("Could not initialize cipher. Please verify the settings.");
            return null;
        }
    }

    private Cipher doBuild(int mode) throws Exception {

        //first open the keystore.
        KeyStore ks = KeyStore.getInstance("JCEKS");
        ks.load(getKeyStoreStream(), settings.getKeyStorePassword().toCharArray());

        //open the key
        Key key = ks.getKey(settings.getKeyAlias(), settings.getKeyPassword().toCharArray());

        if (key == null) {
            //key has not been found!
            logger.error("Could not find key with alias {} in keystore!", settings.getKeyAlias());
            throw new RuntimeException("Encryption key not found!");
        }

        //we initialize the cipher, we only support ciphers with init vector.
        if (!IV_OP_MODES.contains(settings.getBlockMode().toUpperCase())) {
            logger.error("Block mode {} not supported!", settings.getBlockMode());
            throw new RuntimeException("Block mode not supported!");
        }

        if (!settings.getAlgorithm().equals(key.getAlgorithm())) {
            logger.error("Selected key ({}) does not match with configured algorithm: {}!!", key.getAlgorithm(), settings.getAlgorithm());
            throw new RuntimeException("Incorrect algorithm");
        }

        String cipherInstance = String.format("%s/%s/%s", settings.getAlgorithm(), settings.getBlockMode(), settings.getPadding());

        Cipher cipher = Cipher.getInstance(cipherInstance);
        IvParameterSpec ivs = buildIvParameterSpec(key, cipher);


        //aaand we finally initialize and return the cipher
        cipher.init(mode, key, ivs);

        return cipher;
    }

    private IvParameterSpec buildIvParameterSpec(Key key, Cipher cipher) {
        //so we start the iv
        byte[] iv = new byte[cipher.getBlockSize()];

        //now we will use the same key as IV
        for(int i = 0 ; i < iv.length ; i++) {
            if (key.getEncoded().length < i) {
                iv[i] = key.getEncoded()[i];
            } else {
                iv[i] = (byte) i;
            }
        }

        return new IvParameterSpec(iv);
    }

    private InputStream getKeyStoreStream() throws IOException {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream is = loader.getResourceAsStream(settings.getKeyStoreLocation());
        if (is != null) {
            return is;
        }

        File ksf = new File(settings.getKeyStoreLocation());

        if (ksf.exists() && ksf.isFile()) {
            return new FileInputStream(ksf);
        }

        logger.error("Could not find keystore {} either on the class path or filesystem.", settings.getKeyStoreLocation());
        throw new RuntimeException("KeyStore Not found!");
        //return null;
    }

}
