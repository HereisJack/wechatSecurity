package com.jack.weChatSecurity.core.cache;

import com.jack.weChatSecurity.core.WeChatUser;
import com.jack.weChatSecurity.core.cache.fifo.FIFOSessionCacheFactory;
import com.jack.weChatSecurity.core.cache.lfu.LFUSessionCacheFactory;
import com.jack.weChatSecurity.core.cache.lru.LRUSessionCacheFactory;
import com.jack.weChatSecurity.core.cache.random.RandomSessionCacheFactory;
import com.jack.weChatSecurity.core.cache.ttl.TTLSessionCacheFactory;

import java.util.Map;

/**
 * sessionCache工厂类
 */
public abstract class SessionCacheFactory {

    public static SessionCache createSessionCache(CacheStrategy cacheStrategy, int capacity, Map<String, WeChatUser> sessions)throws Exception{
        SessionCacheFactory sessionCacheFactory=null;
        if (cacheStrategy==CacheStrategy.FIFO){
            sessionCacheFactory=new FIFOSessionCacheFactory();
        }
        if (cacheStrategy==CacheStrategy.LRU){
            sessionCacheFactory=new LRUSessionCacheFactory();
        }
        if (cacheStrategy==CacheStrategy.LFU){
            sessionCacheFactory=new LFUSessionCacheFactory();
        }
        if (cacheStrategy==CacheStrategy.TTL){
            sessionCacheFactory=new TTLSessionCacheFactory();
        }
        if (cacheStrategy==CacheStrategy.RANDOM){
            sessionCacheFactory=new RandomSessionCacheFactory();
        }
        SessionCache sessionCache = sessionCacheFactory.doCreateSessionCache(capacity);
        if (sessions!=null){
            sessionCache.loadSessions(sessions);
        }
        return sessionCache;
    }

    protected abstract SessionCache doCreateSessionCache(int capacity);

}
