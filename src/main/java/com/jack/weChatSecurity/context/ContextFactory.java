package com.jack.weChatSecurity.context;

public abstract class ContextFactory {

    public abstract AuthContext createAuthContext()throws Exception;

    public abstract SecurityContext createSecurityContext()throws Exception;

    public abstract WeChatContext createWechatContext()throws Exception;

}
