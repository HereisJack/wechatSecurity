package com.jack.weChatSecurity.core.au.exception;

import com.jack.weChatSecurity.core.AuthenticateException;

public class ExpiredTokenException extends AuthenticateException {

    public ExpiredTokenException() {
        super();
    }

    public ExpiredTokenException(String message) {
        super(message);
    }

    public ExpiredTokenException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExpiredTokenException(Throwable cause) {
        super(cause);
    }
}
