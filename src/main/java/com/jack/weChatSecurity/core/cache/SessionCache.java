package com.jack.weChatSecurity.core.cache;

import com.jack.weChatSecurity.core.WeChatUser;

import java.util.Map;

/**
 * Session缓存
 */
public interface SessionCache {

    /**
     * 获取session
     * @param key
     * @return null值表明不存在
     */
    WeChatUser getSession(String key);
    /**
     * 移除session
     * @param key
     */
    void removeSession(String key);

    /**
     * 新增session
     * @param key
     * @param value
     */
    void putSession(String key,WeChatUser value);

    /**
     * 装载外部sessions
     * @param sessions
     */
    void loadSessions(Map<String,WeChatUser> sessions);

    /**
     * 返回内存中的sessions,规定输出为<String,WeChatUser>为标准是
     * 为了方便可以随时配置更改内存淘汰策略
     * @return
     */
    Map<String,WeChatUser> serializable();

    /**
     * 返回容量
     * @return
     */
    int getCapacity();

    /**
     * 返回缓存淘汰策略
     * @return
     */
    CacheStrategy getCacheStrategy();
}
