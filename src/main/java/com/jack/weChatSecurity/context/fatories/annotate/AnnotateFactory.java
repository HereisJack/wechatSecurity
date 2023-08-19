package com.jack.weChatSecurity.context.fatories.annotate;

import com.jack.weChatSecurity.context.*;
import com.jack.weChatSecurity.context.config.SecurityConfig;
import com.jack.weChatSecurity.context.config.UrlConfig;
import com.jack.weChatSecurity.context.config.WeChatConfig;
import com.jack.weChatSecurity.context.exception.NotFindDatasourceException;
import com.jack.weChatSecurity.context.exception.NotFindWeChatSecurityConfigException;
import com.jack.weChatSecurity.context.Config;
import com.jack.weChatSecurity.context.Datasource;
import com.jack.weChatSecurity.utils.AnnotationUtil;
import com.jack.weChatSecurity.utils.ClassUtil;
import com.jack.weChatSecurity.utils.ReflectUtil;

import java.lang.annotation.Annotation;
import java.util.LinkedList;
import java.util.List;

public class AnnotateFactory extends ContextFactory {
    private String packagePath;
    private List<Class> classes;
    private WeChatSecurityConfig weChatSecurityConfig;

    public AnnotateFactory(String packagePath)throws Exception{
        this.packagePath=trimPackagePath(packagePath);
        this.classes= ClassUtil.getClassByPackage(this.packagePath);
        Class<WeChatSecurityConfig> config = findConfig();
        if (config==null)
            throw new NotFindWeChatSecurityConfigException("Not find config class!");
        if (WeChatSecurityConfig.class.isAssignableFrom(config)){
            throw new ContextInitException("Config class not implements WeChatSecurityConfig!");
        }
        this.weChatSecurityConfig= ReflectUtil.newInstance(config);
    }

    @Override
    public WeChatContext createWechatContext()throws Exception {
        WeChatConfig weChatConfig=new WeChatConfig();
        this.weChatSecurityConfig.setWeChatConfig(weChatConfig);
        return weChatConfig;
    }

    @Override
    public SecurityContext createSecurityContext()throws Exception {
        Datasource dataSource = this.weChatSecurityConfig.getDataSource();
        if(dataSource==null)
            throw new NotFindDatasourceException("");
        AnnotateSecurityContext securityContext=new AnnotateSecurityContext(dataSource);
        SecurityConfig config=new SecurityConfig();
        this.weChatSecurityConfig.setSecurityConfig(config);
        //初始化SecurityContext
        securityContext.init(config);
        return securityContext;
    }

    @Override
    public AuthContext createAuthContext() throws Exception{
        UrlConfig config=new UrlConfig();
        weChatSecurityConfig.setUrlConfig(config);
        return config;
    }

    private Class findConfig(){
        Class targetClass=null;
        for (Class cls:classes){
            if(AnnotationUtil.containsInClass(cls, Config.class)) {
                targetClass = cls;
                break;
            }
        }
        return targetClass;
    }

    private List<Class> findConfigs(){
        List<Class> list=new LinkedList<>();
        for (Class cls:classes){
            if(AnnotationUtil.containsInClass(cls, Config.class))
                list.add(cls);
        }
        return list;
    }

    private String trimPackagePath(String packagePath)throws ContextInitException{
        if ("".equals(packagePath))
            throw new ContextInitException("package path is null!");
        packagePath=packagePath.replaceAll("/",".");
        return packagePath;
    }
}
