package com.jack.weChatSecurity.core.spring.aop;

import java.lang.reflect.Method;
import java.util.List;

public class AspectChain {
    private List<Aspect> aspects;
    private int count;
    private int capacity;

    private Object target;
    private Class targetClass;
    private Method method;
    private Object[] args;

    public AspectChain(List<Aspect> aspects,Object target,Method method,Object[] args){
        this.aspects=aspects;
        this.capacity=aspects.size();
        this.count=0;
        this.target=target;
        this.targetClass=target.getClass();
        this.method=method;
        this.args=args;
    }

    public Object doAspectChain()throws Exception{
        if (this.count<this.capacity){
            Aspect aspect = this.aspects.get(this.count++);
            return aspect.doAspect(this);
        }
        else {
            return this.method.invoke(this.target,this.args);
        }
    }

    public Class getTargetClass() {
        return targetClass;
    }

    public Method getMethod() {
        return method;
    }
}
