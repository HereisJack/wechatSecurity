package com.jack.weChatSecurity.core;

import com.jack.weChatSecurity.context.SecurityContext;
import com.jack.weChatSecurity.core.monitor.SecurityMonitor;
import com.jack.weChatSecurity.core.monitor.SerializableMonitor;

import java.util.HashMap;
import java.util.Map;

public class MonitorManager {

    private Map<String, SecurityMonitor> monitors;

    protected MonitorManager(){
        this.monitors=new HashMap<>();
        //注册默认的Monitor
        SerializableMonitor serializableMonitor=new SerializableMonitor();
        registerMonitor("serializableMonitor",serializableMonitor);
    }

    /**
     * 注册Monitor
     * @param key
     * @param monitor
     */
    protected void registerMonitor(String key,SecurityMonitor monitor){
        if (monitor!=null) {
            this.monitors.put(key,monitor);
        }
    }

    /**
     * 获取Monitor
     * @param key
     * @return
     */
    public SecurityMonitor getMonitor(String key){
        SecurityMonitor monitor=null;
        if (this.monitors.containsKey(key)){
            monitor=this.monitors.get(key);
        }
        return monitor;
    }

    /**
     * 初始化monitor并启动
     * @param securityContext
     * @throws Exception
     */
    protected void init(SecurityContext securityContext)throws Exception{
        for (Map.Entry<String,SecurityMonitor> entry:monitors.entrySet()) {
            String key = entry.getKey();
            SecurityMonitor value = entry.getValue();
            Thread thread = new Thread(value, key);
            value.init(securityContext,thread);
            thread.start();
        }
    }

    /**
     * monitor销毁回调函数
     * @throws Exception
     */
    protected void destroy()throws Exception{
        for (SecurityMonitor monitor:monitors.values())
            monitor.destroy();
    }
}
