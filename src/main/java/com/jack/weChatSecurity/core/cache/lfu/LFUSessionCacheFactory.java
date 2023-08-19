package com.jack.weChatSecurity.core.cache.lfu;

import com.jack.weChatSecurity.core.cache.CacheStrategy;
import com.jack.weChatSecurity.core.cache.SessionCache;
import com.jack.weChatSecurity.core.cache.SessionCacheFactory;

public class LFUSessionCacheFactory extends SessionCacheFactory {

    @Override
    protected SessionCache doCreateSessionCache(int capacity) {
        return new LFUSessionCache(CacheStrategy.LFU,capacity);
    }
}
