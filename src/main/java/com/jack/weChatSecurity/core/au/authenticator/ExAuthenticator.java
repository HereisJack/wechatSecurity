package com.jack.weChatSecurity.core.au.authenticator;

import com.jack.weChatSecurity.context.AuthContext;
import com.jack.weChatSecurity.core.AuthenticateException;
import com.jack.weChatSecurity.core.AuthenticatorChain;
import com.jack.weChatSecurity.core.au.Authenticator;
import com.jack.weChatSecurity.core.au.Request;
import com.jack.weChatSecurity.core.urlmatcher.UrlMatcher;
import com.jack.weChatSecurity.core.urlmatcher.UrlMatcherImpl;

public class ExAuthenticator implements Authenticator {
    private UrlMatcher urlMatcher=new UrlMatcherImpl();

    @Override
    public void doAuthenticate(Request request, AuthenticatorChain authenticatorChain) throws AuthenticateException {
        boolean matcher = urlMatcher.matcher(request);
        if(!matcher){
            authenticatorChain.doAuthenticatorChain(request);
        }
        else return;
    }

    @Override
    public void init(AuthContext authContext) {
        this.urlMatcher.init(authContext.getPassUrl());
    }
}
