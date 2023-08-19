package com.jack.weChatSecurity.context;

import java.util.List;

public interface AuthContext {

    String getBaseUrl();

    List<String> getInterceptUrl();

    List<String> getPassUrl();

}
