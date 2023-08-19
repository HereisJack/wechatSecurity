package com.jack.weChatSecurity.core.au;

import com.jack.weChatSecurity.context.AuthContext;
import com.jack.weChatSecurity.core.AuthenticateException;
import com.jack.weChatSecurity.core.AuthenticatorChain;

public interface Authenticator {

    default void init(AuthContext authContext){};

    void doAuthenticate(Request request, AuthenticatorChain authenticatorChain)throws AuthenticateException;

}
