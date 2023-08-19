package com.jack.weChatSecurity.core;

import com.jack.weChatSecurity.context.AuthContext;
import com.jack.weChatSecurity.core.au.Authenticator;
import com.jack.weChatSecurity.core.au.Request;
import com.jack.weChatSecurity.core.au.authenticator.ExAuthenticator;
import com.jack.weChatSecurity.core.au.authenticator.RolesAuthenticator;
import com.jack.weChatSecurity.core.urlmatcher.urltrie.UrlResolver;

import java.util.LinkedList;
import java.util.List;

public class AuthenticatorChain {
    private List<Authenticator> authenticators;
    private ThreadLocal<Integer> count;

    public AuthenticatorChain(){
        authenticators=new LinkedList<>();
        registerAuthenticator(new ExAuthenticator());
        registerAuthenticator(new TokenAuthenticator());
        registerAuthenticator(new RolesAuthenticator());
        count=ThreadLocal.withInitial(()->0);
    }

    public void registerAuthenticator(Authenticator authenticator){
        authenticators.add(authenticator);
    }

    protected void initAuthenticator(AuthContext authContext){
        UrlResolver.setBaseUrl(authContext.getBaseUrl());
        for(Authenticator authenticator:authenticators){
            authenticator.init(authContext);
        }
    }

    public void doAuthenticatorChain(Request request)throws AuthenticateException{
        if(count.get()<authenticators.size()){
            Authenticator authenticator = authenticators.get(count.get());
            count.set(count.get()+1);
            authenticator.doAuthenticate(request,this);
        }
        //调用完认证链后释放资源
        else count.remove();
    }

}
