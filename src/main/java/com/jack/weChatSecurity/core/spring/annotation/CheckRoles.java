package com.jack.weChatSecurity.core.spring.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD})
public @interface CheckRoles {

    String[] value();

}
