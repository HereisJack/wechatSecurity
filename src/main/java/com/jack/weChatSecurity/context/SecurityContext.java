package com.jack.weChatSecurity.context;

import com.jack.weChatSecurity.core.MonitorManager;
import com.jack.weChatSecurity.core.cache.CacheStrategy;
import com.jack.weChatSecurity.core.cache.SessionCache;

import java.io.File;

public interface SecurityContext {

    /**
     * 获取session缓存
     * @return
     */
    SessionCache getSessionCache();

    /**
     * 获取MonitorManager
     * @return
     */
    MonitorManager getMonitorManager();

    String getSessionLocation();

    String getSessionFileName();

    long getSavingTime();

}
