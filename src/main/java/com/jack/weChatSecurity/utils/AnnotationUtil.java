package com.jack.weChatSecurity.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

public final class AnnotationUtil {

    public static Annotation findInClass(Class<?> cls,Class<? extends Annotation> annotation){
        Annotation anno=null;
        if(cls.isAnnotationPresent(annotation)){
            anno=cls.getAnnotation(annotation);
        }
        return anno;
    }

    public static List<Annotation> findInAllField(Class<?> cls,Class<? extends Annotation> annotation){
        List<Annotation> list=new LinkedList<>();
        Field[] declaredFields = cls.getDeclaredFields();
        for(Field field:declaredFields){
            if(field.isAnnotationPresent(annotation)){
                list.add(field.getAnnotation(annotation));
            }
        }
        return list;
    }

    public static List<Annotation> findInAllMethod(Class<?> cls,Class<? extends Annotation> annotation){
        List<Annotation> list=new LinkedList<>();
        Method[] declaredMethods = cls.getDeclaredMethods();
        for(Method method:declaredMethods){
            if(method.isAnnotationPresent(annotation)){
                list.add(method.getAnnotation(annotation));
            }
        }
        return list;
    }

    public static boolean containsInClass(Class<?> cls,Class<? extends Annotation> annotation){
        if (cls==null||annotation==null)
            return false;
        return cls.isAnnotationPresent(annotation);
    }

    public static boolean containsInAllField(Class<?> cls,Class<? extends Annotation> annotation){
        if (cls==null||annotation==null)
            return false;
        Field[] declaredFields = cls.getDeclaredFields();
        for(Field field:declaredFields){
            if(field.isAnnotationPresent(annotation)){
                return true;
            }
        }
        return false;
    }

    public static boolean containsInAllMethod(Class<?> cls,Class<? extends Annotation> annotation){
        if (cls==null||annotation==null)
            return false;
        Method[] declaredMethods = cls.getDeclaredMethods();
        for(Method method:declaredMethods){
            if(method.isAnnotationPresent(annotation)){
                return true;
            }
        }
        return false;
    }

}
