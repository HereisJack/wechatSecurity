package com.jack.weChatSecurity.context.fatories.annotate;

import com.jack.weChatSecurity.context.Datasource;
import com.jack.weChatSecurity.context.exception.NotFindDatasourceException;
import com.jack.weChatSecurity.core.SecurityContextBase;

public class AnnotateSecurityContext extends SecurityContextBase {

    public AnnotateSecurityContext(Datasource datasource)throws NotFindDatasourceException {
        setDatasource(datasource);
    }
}
