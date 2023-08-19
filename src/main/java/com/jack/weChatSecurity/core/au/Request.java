package com.jack.weChatSecurity.core.au;

import com.jack.weChatSecurity.core.urlmatcher.urltrie.Power;

import java.util.Map;

public class Request {

    private String url;
    private Map<String,String[]> params;
    private Power power;

    public Request(String url,Map<String,String[]> params){
        this.url=url;
        this.params=params;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setParams(Map<String, String[]> params) {
        this.params = params;
    }

    public void setPower(Power power) {
        this.power = power;
    }

    public String getUrl() {
        return url;
    }

    public Power getPower() {
        return power;
    }

    public String getParam(String name){
        return params.get(name)[0];
    }

    @Override
    public String toString() {
        return "Request{" +
                "url='" + url + '\'' +
                ", power=" + power +
                '}';
    }
}
