package com.jack.weChatSecurity.context.exception;

import com.jack.weChatSecurity.context.ContextInitException;

public class NotFindWeChatSecurityConfigException extends ContextInitException {

    public NotFindWeChatSecurityConfigException() {
        super();
    }

    public NotFindWeChatSecurityConfigException(String message) {
        super(message);
    }

    public NotFindWeChatSecurityConfigException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFindWeChatSecurityConfigException(Throwable cause) {
        super(cause);
    }
}
