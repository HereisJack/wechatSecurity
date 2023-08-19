package com.jack.weChatSecurity.core.monitor;


import com.jack.weChatSecurity.context.SecurityContext;

/**
 * 监听Security
 */
public interface SecurityMonitor extends Runnable{

    /**
     * Monitor的初始生命周期
     */
    void init(SecurityContext securityContext,Thread monitor) throws Exception;

    /**
     * Monitor的结束生命周期
     */
    void destroy()throws Exception;

}
