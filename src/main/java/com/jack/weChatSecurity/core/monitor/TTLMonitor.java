package com.jack.weChatSecurity.core.monitor;

import com.jack.weChatSecurity.context.SecurityContext;
import com.jack.weChatSecurity.core.cache.ttl.TTLSessionCache;

public class TTLMonitor extends SecurityMonitorBase{

    @Override
    public void doRun(SecurityContext securityContext) throws Exception {
        TTLSessionCache ttlSessionCache = (TTLSessionCache) securityContext.getSessionCache();
        while (live()){
            ttlSessionCache.ttlSessions();
            Thread.sleep(1000);
        }
    }

    @Override
    public void doInit(SecurityContext securityContext) throws Exception {
        //do nothing
        System.out.println("TTLMonitor init!");
    }

    @Override
    public void doDestroy(SecurityContext securityContext) throws Exception {
        //do nothing
        System.out.println("TTLMonitor closed!");
    }
}
