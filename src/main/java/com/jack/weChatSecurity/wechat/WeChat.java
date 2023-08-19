package com.jack.weChatSecurity.wechat;

import com.jack.weChatSecurity.wechat.bean.accessToken;
import com.jack.weChatSecurity.wechat.bean.code2Session;

/**
 * WeChat
 * 与微信服务端交互的接口
 * 所有接口参考微信小程序开发文档编写
 * 可结合微信小程序开发文档查看
 */
public interface WeChat {

    code2Session auth_code2Session(String code)throws Exception;

    accessToken auth_getAccessToken()throws Exception;
}
