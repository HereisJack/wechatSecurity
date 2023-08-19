package com.jack.weChatSecurity.core.cache.fifo;

import com.jack.weChatSecurity.core.cache.CacheStrategy;
import com.jack.weChatSecurity.core.cache.SessionCache;
import com.jack.weChatSecurity.core.cache.SessionCacheFactory;


public class FIFOSessionCacheFactory extends SessionCacheFactory {

    @Override
    protected SessionCache doCreateSessionCache(int capacity) {
        return new FIFOSessionCache(CacheStrategy.FIFO,capacity);
    }
}
