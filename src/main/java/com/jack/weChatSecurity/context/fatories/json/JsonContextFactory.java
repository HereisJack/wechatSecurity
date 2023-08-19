package com.jack.weChatSecurity.context.fatories.json;

import com.jack.weChatSecurity.context.*;
import com.jack.weChatSecurity.context.config.SecurityConfig;
import com.jack.weChatSecurity.json.JSON;
import com.jack.weChatSecurity.utils.StreamUtil;

public class JsonContextFactory extends ContextFactory {
    private String config;
    private JsonConfig jsonConfig;

    public JsonContextFactory()throws Exception{
        this("wechatSecurity.json");
    }

    public JsonContextFactory(String filename)throws Exception{
        config =trimConfigFilename(filename);
        String json = StreamUtil.getStringFromStream(Thread.currentThread().getContextClassLoader().getResourceAsStream(config));
        jsonConfig= JSON.readBean(json, JsonConfig.class);
    }

    @Override
    public AuthContext createAuthContext() throws Exception {
        return jsonConfig.getUrlConfig();
    }

    @Override
    public SecurityContext createSecurityContext() throws Exception {
        SecurityConfig securityConfig = jsonConfig.getSecurityConfig();
        JsonSecurityContext securityContext=new JsonSecurityContext(jsonConfig.getDatasourcePath());
        //初始化SecurityContext
        securityContext.init(securityConfig);
        return securityContext;
    }

    @Override
    public WeChatContext createWechatContext() throws Exception {
        return jsonConfig.getWeChatConfig();
    }

    private String trimConfigFilename(String config)throws ContextInitException{
        if ("".equals(config))
            throw new ContextInitException("configFilename is null!");
        if (!config.endsWith(".json"))
            throw new ContextInitException("config file is not json suffix!");
        return config;
    }
}
