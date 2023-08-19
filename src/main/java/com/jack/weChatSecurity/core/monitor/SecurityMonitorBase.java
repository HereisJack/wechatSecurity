package com.jack.weChatSecurity.core.monitor;

import com.jack.weChatSecurity.context.SecurityContext;

public abstract class SecurityMonitorBase implements SecurityMonitor {
    private SecurityContext securityContext;
    private Thread monitor;
    private boolean live=true;

    protected boolean live(){
        return this.live;
    }

    @Override
    public void run() {
        try {
            doRun(this.securityContext);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init(SecurityContext securityContext,Thread monitor) throws Exception {
        this.securityContext=securityContext;
        this.monitor=monitor;
        doInit(securityContext);
    }

    @Override
    public void destroy()throws Exception {
        this.live=false;
        //防止资源提前释放
        while (monitor.isAlive()) {
            //中断线程
            monitor.interrupt();
            System.out.println(monitor.getName()+"  interrupt!!");
            //主线程让出执行权希望交给等待响应中断的线程
            Thread.sleep(100);
        }
        doDestroy(this.securityContext);
    }

    public abstract void doRun(SecurityContext securityContext)throws Exception;
    public abstract void doInit(SecurityContext securityContext) throws Exception;
    public abstract void doDestroy(SecurityContext securityContext)throws Exception;
}
