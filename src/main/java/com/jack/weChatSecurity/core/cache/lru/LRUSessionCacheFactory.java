package com.jack.weChatSecurity.core.cache.lru;

import com.jack.weChatSecurity.core.WeChatUser;
import com.jack.weChatSecurity.core.cache.CacheStrategy;
import com.jack.weChatSecurity.core.cache.SessionCache;
import com.jack.weChatSecurity.core.cache.SessionCacheBase;
import com.jack.weChatSecurity.core.cache.SessionCacheFactory;

import java.util.Map;

public class LRUSessionCacheFactory extends SessionCacheFactory {

    @Override
    protected SessionCache doCreateSessionCache(int capacity) {
        return new LRUSessionCache(CacheStrategy.LRU,capacity);
    }
}
