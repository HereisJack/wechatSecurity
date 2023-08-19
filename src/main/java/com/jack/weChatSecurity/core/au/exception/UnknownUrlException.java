package com.jack.weChatSecurity.core.au.exception;

import com.jack.weChatSecurity.core.AuthenticateException;

public class UnknownUrlException extends AuthenticateException {

    public UnknownUrlException() {
        super();
    }

    public UnknownUrlException(String message) {
        super(message);
    }

    public UnknownUrlException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownUrlException(Throwable cause) {
        super(cause);
    }

}
