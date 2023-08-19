package com.jack.weChatSecurity.core;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class DefaultWeChatUser implements WeChatUser, Serializable {
    private static final long serialVersionUID = 2L;

    //session的token
    private String token;
    //微信open_id
    private String id;
    //第一次认证时间
    private Timestamp registerTime;
    //最后查看或修改时间
    private Timestamp lastModifyTime;
    //Remember的时间
    private Timestamp rememberMe;
    //数据
    private Map<String,Object> data;
    //角色权限
    private Set<String> roles;

    public DefaultWeChatUser(){
        this.registerTime=new Timestamp(System.currentTimeMillis());
        this.lastModifyTime=this.registerTime;
        this.rememberMe=this.registerTime;
        this.data=new HashMap<>();
        this.roles=new HashSet<>();
    }

    protected void setToken(String token) {
        this.token=token;
    }

    protected void setId(String id) {
        this.id = id;
    }

    protected void setRoles(Set<String> roles){this.roles=roles;}

    protected Timestamp getRememberMe(){return this.rememberMe;}

    @Override
    public String getToken() {
        return token;
    }

    @Override
    public long getRegisterTime() {
        return registerTime.getTime();
    }

    @Override
    public long getLastModifyTime() {
        return lastModifyTime.getTime();
    }

    @Override
    public void modify() {
        this.lastModifyTime=new Timestamp(System.currentTimeMillis());
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public <T> void setAttribute(String name, T value) {
        data.put(name,value);
    }

    @Override
    public <T> T getAttribute(String name) {
        return (T)data.get(name);
    }

    @Override
    public boolean hasRole(String role) {
        return this.roles.contains(role);
    }

    @Override
    public boolean hasRoles(String... roles) {
        boolean b=false;
        for(String role:roles){
            b=hasRole(role);
        }
        return b;
    }

    @Override
    public void rememberMe(long time) {
       this.rememberMe=new Timestamp(System.currentTimeMillis()+time);
    }
}
