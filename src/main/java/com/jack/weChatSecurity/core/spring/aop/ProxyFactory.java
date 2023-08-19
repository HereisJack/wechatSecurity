package com.jack.weChatSecurity.core.spring.aop;

/**
 * 代理工厂类
 * 生成代理
 */
public interface ProxyFactory {

    /**
     * 创建代理对象
     * @param target 被代理的对象
     * @return 如果有注解支持就提供代理对象，否则原对象返回
     * @throws Exception
     */
    Object createProxy(Object target);

}
