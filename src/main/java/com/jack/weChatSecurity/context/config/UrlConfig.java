package com.jack.weChatSecurity.context.config;

import com.jack.weChatSecurity.context.AuthContext;

import java.util.LinkedList;
import java.util.List;

public class UrlConfig implements AuthContext {
    private String baseUrl;
    private List<String> interceptUrl=new LinkedList<>();
    private List<String> passUrl=new LinkedList<>();

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public List<String> getInterceptUrl() {
        return interceptUrl;
    }

    public void setInterceptUrl(List<String> interceptUrl) {
        this.interceptUrl = interceptUrl;
    }

    public UrlConfig addInterceptUrl(String interceptUrl){
        this.interceptUrl.add(interceptUrl);
        return this;
    }

    public List<String> getPassUrl() {
        return passUrl;
    }

    public void setPassUrl(List<String> passUrl) {
        this.passUrl = passUrl;
    }

    public UrlConfig addPassUrl(String passUrl){
        this.passUrl.add(passUrl);
        return this;
    }
}
