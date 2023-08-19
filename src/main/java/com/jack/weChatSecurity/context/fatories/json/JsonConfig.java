package com.jack.weChatSecurity.context.fatories.json;

import com.jack.weChatSecurity.context.config.SecurityConfig;
import com.jack.weChatSecurity.context.config.UrlConfig;
import com.jack.weChatSecurity.context.config.WeChatConfig;

public class JsonConfig {
    private WeChatConfig weChatConfig;
    private SecurityConfig securityConfig;
    private UrlConfig urlConfig;
    private String datasourcePath;

    public String getDatasourcePath() {
        return datasourcePath;
    }

    public void setDatasourcePath(String datasourcePath) {
        this.datasourcePath = datasourcePath;
    }

    public WeChatConfig getWeChatConfig() {
        return weChatConfig;
    }

    public void setWeChatConfig(WeChatConfig weChatConfig) {
        this.weChatConfig = weChatConfig;
    }

    public SecurityConfig getSecurityConfig() {
        return securityConfig;
    }

    public void setSecurityConfig(SecurityConfig securityConfig) {
        this.securityConfig = securityConfig;
    }

    public UrlConfig getUrlConfig() {
        return urlConfig;
    }

    public void setUrlConfig(UrlConfig urlConfig) {
        this.urlConfig = urlConfig;
    }
}
