package com.jack.weChatSecurity.core.token;

import com.jack.weChatSecurity.core.WeChatUser;

import java.util.UUID;

public class DefaultTokenGenerator implements TokenGenerator {

    @Override
    public String createToken(WeChatUser user) {
        return UUID.randomUUID().toString();
    }

}
