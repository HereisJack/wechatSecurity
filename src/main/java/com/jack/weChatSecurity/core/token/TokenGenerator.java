package com.jack.weChatSecurity.core.token;

import com.jack.weChatSecurity.core.WeChatUser;

public interface TokenGenerator {

    String createToken(WeChatUser user);
}
