package com.jack.weChatSecurity.context.fatories.annotate;

import com.jack.weChatSecurity.context.config.SecurityConfig;
import com.jack.weChatSecurity.context.config.UrlConfig;
import com.jack.weChatSecurity.context.config.WeChatConfig;
import com.jack.weChatSecurity.context.Datasource;

public interface WeChatSecurityConfig {

    void setWeChatConfig(WeChatConfig weChatConfig);

    void setSecurityConfig(SecurityConfig securityConfig);

    void setUrlConfig(UrlConfig urlConfig);

    Datasource getDataSource();
}
