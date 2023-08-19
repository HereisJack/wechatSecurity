package com.jack.weChatSecurity.core.spring.aop;

import com.jack.weChatSecurity.core.spring.annotation.CheckRole;
import com.jack.weChatSecurity.core.spring.annotation.CheckRoles;
import com.jack.weChatSecurity.core.spring.aop.aspects.CheckRoleAspect;
import com.jack.weChatSecurity.core.spring.aop.aspects.CheckRolesAspect;

import java.lang.annotation.Annotation;
import java.util.*;

public final class AspectRegister {
    private Map<Class<? extends Annotation>,Aspect> aspectMap=new HashMap<>();

    public AspectRegister(){
        registerAspect(CheckRole.class, new CheckRoleAspect());
        registerAspect(CheckRoles.class, new CheckRolesAspect());
    }

    public void registerAspect(Class<? extends Annotation> annotation,Aspect aspect){
        aspectMap.put(annotation, aspect);
    }

    public List<Aspect> getAspects(List<Class<? extends Annotation>> annotations){
        List<Aspect> list=new ArrayList<>();
        for (Class<? extends Annotation> cls:annotations)
            if (aspectMap.containsKey(cls))
                list.add(aspectMap.get(cls));
        return list;
    }

    public Set<Class<? extends Annotation>> getKeys(){
        return aspectMap.keySet();
    }
}
