package com.jack.weChatSecurity.core.au.exception;

import com.jack.weChatSecurity.core.AuthenticateException;

public class IllegalRoleException extends AuthenticateException {

    public IllegalRoleException() {
        super();
    }

    public IllegalRoleException(String message) {
        super(message);
    }

    public IllegalRoleException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalRoleException(Throwable cause) {
        super(cause);
    }
}
