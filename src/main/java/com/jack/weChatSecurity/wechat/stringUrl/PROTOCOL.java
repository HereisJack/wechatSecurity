package com.jack.weChatSecurity.wechat.stringUrl;

public enum PROTOCOL {
    HTTP("http"),HTTPS("https"),WS("ws");
    private String protocol;
    private PROTOCOL(String value){
        this.protocol=value;
    }

    public String value(){
        return protocol;
    }
}
