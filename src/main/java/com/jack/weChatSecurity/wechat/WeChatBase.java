package com.jack.weChatSecurity.wechat;

import com.jack.weChatSecurity.context.WeChatContext;

public abstract class WeChatBase {
    private static String APP_ID="wxc51856e094e99c5d";
    private static String SECRET="0a80f304a97eb22549e472032e67ebf6";
    private static String HOST="api.weixin.qq.com";

    public WeChatBase(WeChatContext weChatContext){
        APP_ID= weChatContext.getAppId();
        SECRET= weChatContext.getSecret();
    }

    protected static String getAppId() {
        return APP_ID;
    }

    protected static String getSecret() {
        return SECRET;
    }

    protected static String getWeiXinHost() {
        return HOST;
    }
}
