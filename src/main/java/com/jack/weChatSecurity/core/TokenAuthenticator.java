package com.jack.weChatSecurity.core;

import com.jack.weChatSecurity.context.AuthContext;
import com.jack.weChatSecurity.core.au.Authenticator;
import com.jack.weChatSecurity.core.au.Request;
import com.jack.weChatSecurity.core.au.exception.ExpiredTokenException;
import com.jack.weChatSecurity.core.au.exception.UnknownUrlException;
import com.jack.weChatSecurity.core.urlmatcher.UrlMatcher;
import com.jack.weChatSecurity.core.urlmatcher.UrlMatcherImpl;

import java.sql.Timestamp;

public class TokenAuthenticator implements Authenticator {
    private UrlMatcher urlMatcher=new UrlMatcherImpl();

    @Override
    public void doAuthenticate(Request request, AuthenticatorChain authenticatorChain) throws AuthenticateException {
        boolean matcher = urlMatcher.matcher(request);
        //要拦截的URL
        if(matcher){
            String token = request.getParam("token");
            SecurityHelper.login(token);
            DefaultWeChatUser defaultWeChatUser = (DefaultWeChatUser)SecurityHelper.getWeChatUser();
            //检查rememberMe是否过期
            Timestamp rememberMe = defaultWeChatUser.getRememberMe();
            Timestamp now = new Timestamp(System.currentTimeMillis());
            if (now.after(rememberMe)) {
                SecurityHelper.logout();
                throw new ExpiredTokenException("过期令牌");
            }
            System.out.println("拦截的URL:\r\n" + request);
            //递交给角色认证器
            authenticatorChain.doAuthenticatorChain(request);
        }
        throw new UnknownUrlException("未知URL");
    }

    @Override
    public void init(AuthContext authContext) {
        this.urlMatcher.init(authContext.getInterceptUrl());
    }
}
