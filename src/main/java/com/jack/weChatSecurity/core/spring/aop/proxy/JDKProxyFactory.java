package com.jack.weChatSecurity.core.spring.aop.proxy;

import com.jack.weChatSecurity.core.spring.aop.Aspect;
import com.jack.weChatSecurity.core.spring.aop.AspectChain;

import java.lang.reflect.Proxy;
import java.util.List;

public class JDKProxyFactory extends ProxyFactoryBase {

    @Override
    public Object doCreateProxy(ClassLoader loader, Class targetClass, Object target, List<Aspect> aspects) {
        Class[] interfaces = targetClass.getInterfaces();
        if (interfaces.length==0)
            throw new IllegalArgumentException(targetClass.getName()+" 没有接口支持");
        return Proxy.newProxyInstance(loader, interfaces, (proxy, method, args) -> {
            return new AspectChain(aspects,target,method,args).doAspectChain();
        });
    }

}
