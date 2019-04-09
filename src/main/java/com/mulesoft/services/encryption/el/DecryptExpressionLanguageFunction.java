/*
 * (c) 2003-2018 MuleSoft, Inc. This software is protected under international copyright
 * law. All use of this software is subject to MuleSoft's Master Subscription Agreement
 * (or other master license agreement) separately entered into in writing between you and
 * MuleSoft. If such an agreement is not in place, you may not use the software.
 */
package com.mulesoft.services.encryption.el;

import com.mulesoft.services.encryption.MuleEncryptionPlugin;
import org.mule.api.MuleContext;
import org.mule.api.el.ExpressionLanguageContext;
import org.mule.api.el.ExpressionLanguageFunction;
import org.mule.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;

public class DecryptExpressionLanguageFunction implements ExpressionLanguageFunction {

    private final MuleContext context;

    private static final Logger logger = LoggerFactory.getLogger(MuleEncryptionPlugin.class);

    public DecryptExpressionLanguageFunction(MuleContext context) {
        this.context = context;
    }

    @Override
    public Object call(Object[] objects, ExpressionLanguageContext expressionLanguageContext) {

        if (objects == null) {
            throw new IllegalArgumentException("Wrong number of arguments!");
        }

        if (objects.length != 1) {
            throw new IllegalArgumentException("decrypt function takes only 1 string or byte array argument!");
        }

        Object obj = objects[0];
        Cipher cipher = null;
        try {
            cipher = MuleEncryptionPlugin.getCipher();

            if (obj instanceof String) {
                return new String(cipher.doFinal((Base64.decode((String)obj))));
            } else if (obj instanceof byte[]) {
                return cipher.doFinal((byte[]) obj);
            } else {
                logger.error("Unsupported object type! Returning untouched");
                return obj;
            }
        } catch (Exception ex) {
            logger.error("Exception while executing decryption, returning value AS IS!", ex);
            return obj;
        } finally {
            if (cipher != null) {
                MuleEncryptionPlugin.returnCipher(cipher);
            }
        }
    }
}
