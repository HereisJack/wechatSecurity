package com.jack.weChatSecurity.core;

public class AuthenticateException extends Exception{

    public AuthenticateException() {
        super();
    }

    public AuthenticateException(String message) {
        super(message);
    }

    public AuthenticateException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthenticateException(Throwable cause) {
        super(cause);
    }
}
