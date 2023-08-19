package com.jack.weChatSecurity.core;

import com.jack.weChatSecurity.context.*;
import com.jack.weChatSecurity.core.au.Request;
import com.jack.weChatSecurity.core.au.exception.UnknownTokenException;
import com.jack.weChatSecurity.wechat.DefaultWeChat;
import com.jack.weChatSecurity.wechat.WeChat;

public final class SecurityHelper {
    private static Security security;
    private static AuthenticatorChain authenticatorChain;
    private static ThreadLocal<WeChatUser> currentUser;
    private static WeChat weChat;

    static {
        authenticatorChain=new AuthenticatorChain();
        currentUser=ThreadLocal.withInitial(()->{return null;});
    }

    private SecurityHelper(){}

    protected static void init(ContextFactory contextFactory)throws Exception{
        SecurityContext securityContext = contextFactory.createSecurityContext();
        AuthContext authContext=contextFactory.createAuthContext();
        WeChatContext weChatContext=contextFactory.createWechatContext();
        if(securityContext!=null&&authContext!=null&&weChatContext!=null) {
            authenticatorChain.initAuthenticator(authContext);
            weChat=new DefaultWeChat(weChatContext);
            security=(Security) securityContext;
        }
    }

    protected static void destroy()throws Exception{
        ((SecurityContextLife)security).destroy();
    }

    public static WeChat getWeChat(){
        return weChat;
    }

    public static WeChatUser getWeChatUser(){
        return currentUser.get();
    }

    public static String register(String code)throws Exception{
        return security.register(code);
    }

    public static void logout(){
        security.logout(currentUser.get().getToken());
    }

    protected static void login(String token)throws UnknownTokenException {
        security.login(token);
    }

    public static SecurityContext getSecurityContext(){
        return (SecurityContext) security;
    }

    protected static void setWeChatUser(WeChatUser user){
        currentUser.set(user);
    }

    protected static void finishService(){
        currentUser.remove();
    }

    protected static void doAuthenticatorChain(Request request)throws AuthenticateException{
        authenticatorChain.doAuthenticatorChain(request);
    }
}
