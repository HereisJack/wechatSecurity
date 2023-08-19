package com.jack.weChatSecurity.core.cache.random;

import com.jack.weChatSecurity.core.WeChatUser;
import com.jack.weChatSecurity.core.cache.CacheStrategy;
import com.jack.weChatSecurity.core.cache.SessionCacheBase;

import java.util.Map;

public class RandomSessionCache extends SessionCacheBase<WeChatUser> {

    public RandomSessionCache(CacheStrategy strategy,int capacity){
        super(strategy, capacity);
    }

    @Override
    public WeChatUser getSession(String key) {
        WeChatUser weChatUser=null;
        if (containsValue(key)) {
            weChatUser = getValue(key);
        }
        return weChatUser;
    }

    @Override
    public void removeSession(String key) {
        if (isEmpty())return;
        removeValue(key);
    }

    @Override
    public void putSession(String key, WeChatUser value) {
        if (containsValue(key)){
            removeValue(key);
        }
        if (isFull()){
            String randomKey = randomKey();
            removeValue(randomKey);
        }
        putValue(key, value);
    }

    @Override
    public Map<String, WeChatUser> doLoadSessions(Map<String, WeChatUser> sessions) {
        return sessions;
    }

    @Override
    public Map<String, WeChatUser> doSerializable(Map<String, WeChatUser> map) {
        return map;
    }
}
