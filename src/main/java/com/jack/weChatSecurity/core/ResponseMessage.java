package com.jack.weChatSecurity.core;

public class ResponseMessage {

    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public ResponseMessage setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public ResponseMessage setMsg(String msg) {
        this.msg = msg;
        return this;
    }
}
