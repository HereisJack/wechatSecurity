package com.jack.weChatSecurity.utils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;

public final class ReflectUtil {

    public static Class loadClass(String path)throws Exception{
        return Thread.currentThread().getContextClassLoader().loadClass(path);
    }

    public static <T> T newInstance(Class<T> cls)throws Exception {
        return cls.newInstance();
    }

    public static Class getGenericTypeFromCollection(Field field)throws Exception {
        if (!Collection.class.isAssignableFrom(field.getType()))
            throw new UnsupportedOperationException("非Collection");
        Type genericType = field.getGenericType();
        ParameterizedType pt = (ParameterizedType) genericType;
        Type type = pt.getActualTypeArguments()[0];
        return (Class<?>) type;
    }
}
