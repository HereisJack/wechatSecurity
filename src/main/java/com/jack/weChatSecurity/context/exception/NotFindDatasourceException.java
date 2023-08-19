package com.jack.weChatSecurity.context.exception;

import com.jack.weChatSecurity.context.ContextInitException;

public class NotFindDatasourceException extends ContextInitException {

    public NotFindDatasourceException() {
        super();
    }

    public NotFindDatasourceException(String message) {
        super(message);
    }

    public NotFindDatasourceException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFindDatasourceException(Throwable cause) {
        super(cause);
    }

}
