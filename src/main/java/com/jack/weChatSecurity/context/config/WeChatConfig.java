package com.jack.weChatSecurity.context.config;

import com.jack.weChatSecurity.context.WeChatContext;

public class WeChatConfig implements WeChatContext {
    private String appId;
    private String secret;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
