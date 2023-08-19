package com.jack.weChatSecurity.core.spring.aop.aspects;

import com.jack.weChatSecurity.core.spring.aop.Aspect;
import com.jack.weChatSecurity.core.spring.aop.AspectChain;

import java.lang.reflect.Method;

public abstract class RolesAspectBase implements Aspect {

    @Override
    public Object doAspect(AspectChain aspectChain) throws Exception {
        Class targetClass = aspectChain.getTargetClass();
        Method fatherMethod = aspectChain.getMethod();
        Method sonMethod = targetClass.getDeclaredMethod(fatherMethod.getName(), fatherMethod.getParameterTypes());
        Object result;
        before(targetClass,sonMethod);
        result = aspectChain.doAspectChain();
        after(targetClass,sonMethod);
        return result;
    }

    public abstract void before(Class cls,Method method)throws Exception;
    public abstract void after(Class cls,Method method)throws Exception;
}
