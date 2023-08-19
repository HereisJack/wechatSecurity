package com.jack.weChatSecurity.core.au.exception;

import com.jack.weChatSecurity.core.AuthenticateException;

public class UnknownTokenException extends AuthenticateException {

    public UnknownTokenException() {
        super();
    }

    public UnknownTokenException(String message) {
        super(message);
    }

    public UnknownTokenException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownTokenException(Throwable cause) {
        super(cause);
    }
}
