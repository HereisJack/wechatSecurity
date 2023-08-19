package com.jack.weChatSecurity.core.cache.ttl;

import com.jack.weChatSecurity.core.cache.CacheStrategy;
import com.jack.weChatSecurity.core.cache.SessionCache;
import com.jack.weChatSecurity.core.cache.SessionCacheFactory;

public class TTLSessionCacheFactory extends SessionCacheFactory {

    @Override
    protected SessionCache doCreateSessionCache(int capacity) {
        return new TTLSessionCache(CacheStrategy.TTL, capacity);
    }
}
