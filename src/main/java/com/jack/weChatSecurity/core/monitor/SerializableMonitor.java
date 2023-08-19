package com.jack.weChatSecurity.core.monitor;

import com.jack.weChatSecurity.context.SecurityContext;
import com.jack.weChatSecurity.core.cache.SessionCacheFactory;
import com.jack.weChatSecurity.utils.SerializableUtil;

import java.io.*;

/**
 * 线程
 * session持久化
 */
public class SerializableMonitor extends SecurityMonitorBase {
    //序列化map的时间间隔
    private long savingTime;
    //上次序列化map的时间
    private long lastModifyTime;

    private File file;
    private ObjectOutputStream out;

    @Override
    public void doRun(SecurityContext securityContext)throws Exception {
        while (live()){
            if(System.currentTimeMillis()-this.lastModifyTime >=this.savingTime){
                this.out.writeObject(securityContext.getSessionCache().serializable());
                this.out.flush();
                this.lastModifyTime =System.currentTimeMillis();
                System.out.println("Save session success!");
            }
            Thread.sleep(this.savingTime);
        }
    }

    @Override
    public void doInit(SecurityContext securityContext) throws Exception {
        String location = securityContext.getSessionLocation();
        String fileName = securityContext.getSessionFileName();
        this.savingTime = securityContext.getSavingTime();
        this.file=new File(location+File.separator+fileName);
        this.out=new ObjectOutputStream(new FileOutputStream(file));
        this.lastModifyTime =System.currentTimeMillis();
    }

    @Override
    public void doDestroy(SecurityContext securityContext) throws Exception {
        this.out.writeObject(securityContext.getSessionCache().serializable());
        this.out.close();
        System.out.println("SerializableMonitor closed!");
    }
}
