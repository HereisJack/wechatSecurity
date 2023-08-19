package com.jack.weChatSecurity.context;

public class ContextInitException extends Exception{

    public ContextInitException() {
        super();
    }

    public ContextInitException(String message) {
        super(message);
    }

    public ContextInitException(String message, Throwable cause) {
        super(message, cause);
    }

    public ContextInitException(Throwable cause) {
        super(cause);
    }
}
