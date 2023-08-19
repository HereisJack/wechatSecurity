package com.jack.weChatSecurity.context;

import com.jack.weChatSecurity.context.config.SecurityConfig;

public interface SecurityContextLife {

    void init(SecurityConfig securityConfig)throws Exception;

    void destroy()throws Exception;

}
