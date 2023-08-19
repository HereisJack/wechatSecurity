package com.jack.weChatSecurity.core.cache.random;

import com.jack.weChatSecurity.core.cache.CacheStrategy;
import com.jack.weChatSecurity.core.cache.SessionCache;
import com.jack.weChatSecurity.core.cache.SessionCacheFactory;


public class RandomSessionCacheFactory extends SessionCacheFactory {

    @Override
    protected SessionCache doCreateSessionCache(int capacity) {
        return new RandomSessionCache(CacheStrategy.RANDOM,capacity);
    }
}
