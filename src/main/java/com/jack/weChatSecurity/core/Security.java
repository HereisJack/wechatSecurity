package com.jack.weChatSecurity.core;

import com.jack.weChatSecurity.core.au.exception.*;

public interface Security {

    /**
     * 登陆
     * @param token
     * @throws UnknownTokenException
     */
    void login(String token)throws  UnknownTokenException;

    /**
     * 注册进sessionMap<token,CurrentUser>
     * @param code
     * @return String 返回Token
     */
    String register(String code)throws Exception;

    /**
     * 注销用户
     * @param token 登录token
     */
    void logout(String token);

}
