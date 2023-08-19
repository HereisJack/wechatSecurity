package com.jack.weChatSecurity.core.spring;

import com.jack.weChatSecurity.core.spring.aop.ProxyFactory;
import com.jack.weChatSecurity.core.spring.aop.proxy.JDKProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class WeChatSecurityAnnotationSupport implements BeanPostProcessor {
    private ProxyFactory proxyFactory=new JDKProxyFactory();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return proxyFactory.createProxy(bean);
    }

}
