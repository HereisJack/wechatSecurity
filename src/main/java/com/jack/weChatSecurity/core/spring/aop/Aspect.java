package com.jack.weChatSecurity.core.spring.aop;

import java.lang.reflect.Method;

/**
 * 切面接口
 */
public interface Aspect {

    /**
     * 基于责任链的调用方式
     * @param aspectChain 切面链
     * @return 方法返回值
     */
    Object doAspect(AspectChain aspectChain)throws Exception;

}
