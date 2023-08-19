package com.jack.weChatSecurity.core.au.authenticator;

import com.jack.weChatSecurity.core.AuthenticateException;
import com.jack.weChatSecurity.core.AuthenticatorChain;
import com.jack.weChatSecurity.core.SecurityHelper;
import com.jack.weChatSecurity.core.WeChatUser;
import com.jack.weChatSecurity.core.au.Authenticator;
import com.jack.weChatSecurity.core.au.Request;
import com.jack.weChatSecurity.core.au.exception.IllegalRoleException;
import com.jack.weChatSecurity.core.urlmatcher.urltrie.Power;

import java.util.Set;

public class RolesAuthenticator implements Authenticator {

    @Override
    public void doAuthenticate(Request request, AuthenticatorChain authenticatorChain) throws AuthenticateException {
        Power power= request.getPower();
        boolean b=false;
        if (!power.roleEmpty()) {
            Set<String> roles = power.getRoles();
            WeChatUser weChatUser = SecurityHelper.getWeChatUser();
            for (String role : roles) {
                if (weChatUser.hasRole(role))
                    b = true;
                    break;
            }
        }else b=true;
        if(b)
            authenticatorChain.doAuthenticatorChain(request);
        else
            throw new IllegalRoleException("角色不具备权限");
    }

}
