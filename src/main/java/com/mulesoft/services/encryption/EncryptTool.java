/*
 * (c) 2003-2018 MuleSoft, Inc. This software is protected under international copyright
 * law. All use of this software is subject to MuleSoft's Master Subscription Agreement
 * (or other master license agreement) separately entered into in writing between you and
 * MuleSoft. If such an agreement is not in place, you may not use the software.
 */
package com.mulesoft.services.encryption;

import org.mule.util.Base64;

import javax.crypto.Cipher;

public class EncryptTool {

    public static void main(String[] args) throws Exception {

        System.out.println("Simple encryption tool! \n" +
                "usage: encrypt-tool <value to encrypt>");

        if (args.length == 0) {
            System.out.printf("Please provide a value to encrypt");
        }

        Cipher cipher = CipherBuilder.builder()
                .withSettings(PluginSettings.builder().fromProperties(System.getProperties()).build())
                .withCipherOperationMode(Cipher.ENCRYPT_MODE)
                .build();

        System.out.println(Base64.encodeBytes(cipher.doFinal(args[0].getBytes())));
    }

}
