/*
 * (c) 2003-2018 MuleSoft, Inc. This software is protected under international copyright
 * law. All use of this software is subject to MuleSoft's Master Subscription Agreement
 * (or other master license agreement) separately entered into in writing between you and
 * MuleSoft. If such an agreement is not in place, you may not use the software.
 */
package com.mulesoft.services.encryption.el;

import org.mule.api.MuleContext;
import org.mule.api.context.MuleContextAware;
import org.mule.api.el.ExpressionLanguageContext;
import org.mule.api.el.ExpressionLanguageExtension;

public class EncryptionExpressionLanguageExtension implements ExpressionLanguageExtension, MuleContextAware {

    private MuleContext context;

    @Override
    public void configureContext(ExpressionLanguageContext expressionLanguageContext) {
        expressionLanguageContext.declareFunction("decrypt", new DecryptExpressionLanguageFunction(context));
    }

    @Override
    public void setMuleContext(MuleContext muleContext) {
        this.context = muleContext;
    }
}
