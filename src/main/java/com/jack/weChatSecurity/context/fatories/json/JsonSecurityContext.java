package com.jack.weChatSecurity.context.fatories.json;

import com.jack.weChatSecurity.context.Datasource;
import com.jack.weChatSecurity.core.SecurityContextBase;
import com.jack.weChatSecurity.utils.ReflectUtil;

public class JsonSecurityContext extends SecurityContextBase {

    public JsonSecurityContext(String datasourcePath)throws Exception{
        Class<Datasource> aClass = ReflectUtil.loadClass(datasourcePath);
        setDatasource(ReflectUtil.newInstance(aClass));
    }
}
