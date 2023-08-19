package com.jack.weChatSecurity.core.spring.aop.aspects;

import com.jack.weChatSecurity.core.SecurityHelper;
import com.jack.weChatSecurity.core.WeChatUser;
import com.jack.weChatSecurity.core.au.exception.IllegalRoleException;
import com.jack.weChatSecurity.core.spring.annotation.CheckRoles;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CheckRolesAspect extends RolesAspectBase {
    private static final Class annotation= CheckRoles.class;

    @Override
    public void before(Class cls, Method method) throws Exception {
        //类级别的注解
        WeChatUser weChatUser = SecurityHelper.getWeChatUser();
        Set<String> roles=new HashSet<>();
        if (cls.isAnnotationPresent(annotation)){
            CheckRoles checkRoles = (CheckRoles)cls.getAnnotation(annotation);
            String[] value = checkRoles.value();
            roles.addAll(Arrays.asList(value));
        }
        //方法级别的注解
        if (method.isAnnotationPresent(annotation)){
            CheckRoles checkRoles = (CheckRoles)method.getAnnotation(annotation);
            String[] value = checkRoles.value();
            roles.addAll(Arrays.asList(value));
        }
        boolean b=true;
        for (String role:roles){
            if (!weChatUser.hasRole(role)){
                b=false;
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
