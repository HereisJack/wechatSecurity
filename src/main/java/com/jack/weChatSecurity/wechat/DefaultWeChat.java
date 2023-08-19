package com.jack.weChatSecurity.wechat;

import com.jack.weChatSecurity.context.WeChatContext;
import com.jack.weChatSecurity.json.JSON;
import com.jack.weChatSecurity.wechat.bean.accessToken;
import com.jack.weChatSecurity.wechat.bean.code2Session;
import com.jack.weChatSecurity.wechat.stringUrl.PROTOCOL;
import com.jack.weChatSecurity.wechat.stringUrl.REQUEST_METHOD;
import com.jack.weChatSecurity.wechat.stringUrl.StringUrl;

import java.util.HashMap;
import java.util.Map;

public class DefaultWeChat extends WeChatBase implements WeChat{
    public DefaultWeChat(WeChatContext weChatContext){
        super(weChatContext);
    }

    @Override
    public code2Session auth_code2Session(String code) throws Exception{
        Map<String,String> params=new HashMap<>();
        params.put("appid",getAppId());
        params.put("secret",getSecret());
        params.put("grant_type","authorization_code");
        params.put("js_code",code);
        StringUrl stringUrl=new StringUrl();
        stringUrl.addHost(getWeiXinHost())
                .addProtocol(PROTOCOL.HTTPS)
                .addRequestMethod(REQUEST_METHOD.GET)
                .addPort(443)
                .addQueryPath("/sns/jscode2session")
                .addParams(params);
        return JSON.readBean(stringUrl.connect(), code2Session.class);
    }

    @Override
    public accessToken auth_getAccessToken() throws Exception {
        Map<String,String> params=new HashMap<>();
        params.put("appid",getAppId());
        params.put("secret",getSecret());
        params.put("grant_type","client_credentia");
        StringUrl stringUrl=new StringUrl();
        stringUrl.addHost(getWeiXinHost())
                .addProtocol(PROTOCOL.HTTPS)
                .addRequestMethod(REQUEST_METHOD.GET)
                .addPort(443)
                .addQueryPath("/cgi-bin/token")
                .addParams(params);
        return JSON.readBean(stringUrl.connect(),accessToken.class);
    }
}
