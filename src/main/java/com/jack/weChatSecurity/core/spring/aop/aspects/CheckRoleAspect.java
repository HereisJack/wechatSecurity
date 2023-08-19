package com.jack.weChatSecurity.core.spring.aop.aspects;

import com.jack.weChatSecurity.core.SecurityHelper;
import com.jack.weChatSecurity.core.WeChatUser;
import com.jack.weChatSecurity.core.au.exception.IllegalRoleException;
import com.jack.weChatSecurity.core.spring.annotation.CheckRole;

import java.lang.reflect.Method;
import java.util.*;

public class CheckRoleAspect extends RolesAspectBase {
    private static final Class annotation= CheckRole.class;

    @Override
    public void before(Class cls, Method method) throws Exception {
        //类级别的注解
        WeChatUser weChatUser = SecurityHelper.getWeChatUser();
        boolean b=false;
        Set<String> roles=new HashSet<>();
        if (cls.isAnnotationPresent(annotation)){
            CheckRole checkRole = (CheckRole)cls.getAnnotation(annotation);
            String[] value = checkRole.value();
            roles.addAll(Arrays.asList(value));
        }
        //方法级别的注解
        if (method.isAnnotationPresent(annotation)){
            CheckRole checkRole = (CheckRole)method.getAnnotation(CheckRoleAspect.annotation);
            String[] value = checkRole.value();
            roles.addAll(Arrays.asList(value));
        }
        for (String role:roles){
            if (weChatUser.hasRole(role)){
                b=true;
                break;
            }
        }
        if (!b)
            throw new IllegalRoleException("角色不具备权限");
    }

    @Override
    public void after(Class cls, Method method) throws Exception {

    }
}
