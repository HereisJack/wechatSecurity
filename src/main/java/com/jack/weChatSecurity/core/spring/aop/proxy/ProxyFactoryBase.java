package com.jack.weChatSecurity.core.spring.aop.proxy;

import com.jack.weChatSecurity.core.spring.aop.Aspect;
import com.jack.weChatSecurity.core.spring.aop.AspectRegister;
import com.jack.weChatSecurity.core.spring.aop.ProxyFactory;
import com.jack.weChatSecurity.utils.AnnotationUtil;

import java.lang.annotation.Annotation;
import java.util.*;

public abstract class ProxyFactoryBase implements ProxyFactory {
    private AspectRegister aspectRegister=new AspectRegister();

    @Override
    public Object createProxy(Object target) {
        List<Aspect> aspects = getAspects(target);
        if (!aspects.isEmpty()){
            ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
            Class cls=target.getClass();
            return doCreateProxy(contextClassLoader,cls,target,aspects);
        }
        else return target;
    }

    public abstract Object doCreateProxy(ClassLoader loader,Class targetClass,Object target,List<Aspect> aspects);

    private List<Aspect> getAspects(Object target){
        Class<?> targetClass = target.getClass();
        Set<Class<? extends Annotation>> keys = this.aspectRegister.getKeys();
        List<Class<? extends Annotation>> list = new LinkedList<>();
        for (Class<? extends Annotation> key:keys){
            if (AnnotationUtil.containsInClass(targetClass, key)
            ||AnnotationUtil.containsInAllMethod(targetClass,key)){
                list.add(key);
            }
        }
        //通过类上的注解获取对应的切面
        List<Aspect> aspects = this.aspectRegister.getAspects(list);
        return aspects;
    }

}
