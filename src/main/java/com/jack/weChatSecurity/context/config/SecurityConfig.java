package com.jack.weChatSecurity.context.config;

import com.jack.weChatSecurity.core.cache.CacheStrategy;

public class SecurityConfig {

    //session序列化文件名
    private String sessionFileName;
    //session序列化文件地点
    private String sessionLocation;
    //每隔多久闪照一次
    private long savingTime;
    //session缓存策略
    private String strategy;
    //session容量
    private int sessionCapacity;
    //ttl时间
    private long ttlTime;

    public CacheStrategy getStrategy() {
        CacheStrategy cacheStrategy=CacheStrategy.FIFO;
        if ("LRU".equals(this.strategy))
            cacheStrategy=CacheStrategy.LRU;
        if ("TTL".equals(this.strategy))
            cacheStrategy=CacheStrategy.TTL;
        if ("RANDOM".equals(this.strategy))
            cacheStrategy=CacheStrategy.RANDOM;
        if ("LFU".equals(this.strategy))
            cacheStrategy=CacheStrategy.LFU;
        return cacheStrategy;
    }

    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }

    public int getSessionCapacity() {
        return sessionCapacity;
    }

    public void setSessionCapacity(int sessionCapacity) {
        this.sessionCapacity = sessionCapacity;
    }

    public String getSessionFileName() {
        return sessionFileName;
    }

    public void setSessionFileName(String sessionFileName) {
        this.sessionFileName = sessionFileName;
    }

    public String getSessionLocation() {
        return sessionLocation;
    }

    public void setSessionLocation(String sessionLocation) {
        this.sessionLocation = sessionLocation;
    }

    public long getTtlTime() {
        return ttlTime;
    }

    public void setTtlTime(long ttlTime) {
        this.ttlTime = ttlTime;
    }

    public long getSavingTime() {
        return savingTime;
    }

    public void setSavingTime(long savingTime) {
        this.savingTime = savingTime;
    }

}
