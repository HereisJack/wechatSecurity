package com.jack.weChatSecurity.core;

import com.jack.weChatSecurity.context.Datasource;
import com.jack.weChatSecurity.context.SecurityContext;
import com.jack.weChatSecurity.context.SecurityContextLife;
import com.jack.weChatSecurity.context.config.SecurityConfig;
import com.jack.weChatSecurity.context.exception.NotFindDatasourceException;
import com.jack.weChatSecurity.core.au.exception.UnknownTokenException;
import com.jack.weChatSecurity.core.cache.CacheStrategy;
import com.jack.weChatSecurity.core.cache.SessionCache;
import com.jack.weChatSecurity.core.cache.SessionCacheFactory;

import com.jack.weChatSecurity.core.cache.ttl.TTLSessionCache;
import com.jack.weChatSecurity.core.monitor.TTLMonitor;
import com.jack.weChatSecurity.core.token.DefaultTokenGenerator;
import com.jack.weChatSecurity.core.token.TokenGenerator;
import com.jack.weChatSecurity.utils.SerializableUtil;
import com.jack.weChatSecurity.wechat.bean.code2Session;

import java.io.File;
import java.util.Map;

public abstract class SecurityContextBase implements Security, SecurityContext, SecurityContextLife {
    private String sessionFileName="session.db";
    private String sessionLocation=System.getProperty("user.dir");
    private long savingTime=3500;
    private MonitorManager monitorManager=new MonitorManager();
    private Datasource datasource;

    private SessionCache sessionCache;

    protected void setDatasource(Datasource datasource)throws NotFindDatasourceException{
        if (datasource==null)
            throw new NotFindDatasourceException("没有数据源!");
        this.datasource=datasource;
    }

    @Override
    public void init(SecurityConfig securityConfig) throws Exception {
        this.sessionFileName = securityConfig.getSessionFileName();
        this.sessionLocation = securityConfig.getSessionLocation();
        this.savingTime = securityConfig.getSavingTime();
        //配置并生成sessionCache
        CacheStrategy strategy = securityConfig.getStrategy();
        int sessionCapacity = securityConfig.getSessionCapacity();
        Object sessions = SerializableUtil.readFromFile(new File(this.sessionLocation + File.separator + this.sessionFileName));
        this.sessionCache = SessionCacheFactory.createSessionCache(strategy, sessionCapacity, (Map<String, WeChatUser>) sessions);
        //所有监听器初始化
        if (strategy==CacheStrategy.TTL){
            ((TTLSessionCache)this.sessionCache).setTtlTime(securityConfig.getTtlTime());
            this.monitorManager.registerMonitor("ttlMonitor",new TTLMonitor());
        }
        this.monitorManager.init(this);
    }

    @Override
    public void destroy() throws Exception {
        this.monitorManager.destroy();
    }

    private TokenGenerator tokenGenerator=new DefaultTokenGenerator();

    @Override
    public void login(String token) throws UnknownTokenException {
        DefaultWeChatUser currentUser =(DefaultWeChatUser) sessionCache.getSession(token);
        if (currentUser==null)
            throw new UnknownTokenException("非法令牌");
        currentUser.modify();
        //注入数据
        currentUser.setRoles(datasource.getRoles());
        SecurityHelper.setWeChatUser(currentUser);
    }

    @Override
    public String register(String code) throws Exception {
        code2Session code2Session =SecurityHelper.getWeChat().auth_code2Session(code);
        DefaultWeChatUser currentUser=new DefaultWeChatUser();
        String newToken=tokenGenerator.createToken(currentUser);
        currentUser.setToken(newToken);
        currentUser.setId(code2Session.getOpenid());
        currentUser.setRoles(datasource.getRoles());
        sessionCache.putSession(newToken,currentUser);
        SecurityHelper.setWeChatUser(currentUser);
        return newToken;
    }

    @Override
    public void logout(String token) {
        sessionCache.removeSession(token);
        SecurityHelper.setWeChatUser(null);
    }

    @Override
    public SessionCache getSessionCache() {
        return this.sessionCache;
    }

    @Override
    public MonitorManager getMonitorManager() {
        return this.monitorManager;
    }

    @Override
    public String getSessionLocation() {
        return this.sessionLocation;
    }

    @Override
    public String getSessionFileName() {
        return this.sessionFileName;
    }

    @Override
    public long getSavingTime() {
        return this.savingTime;
    }

}
