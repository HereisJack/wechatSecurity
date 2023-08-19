package com.jack.weChatSecurity.core.urlmatcher.urltrie;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public final class UrlResolver implements PathSeparator {

    private static String BASE_URL = "";

    public static void setBaseUrl(String baseUrl){
        if(baseUrl!=null)
            BASE_URL =baseUrl;
    }

    public static List<AuUrl> resolveUrl(List<String> urls){
        List<AuUrl> result=new LinkedList<>();
        for(String url:urls){
            result.add(doResolve(url));
        }
        return result;
    }

    private static AuUrl doResolve(String url){
        AuUrl auUrl;
        if(url.startsWith(ROLE_BEGIN)){
            auUrl=doRoleUrl(url);
        }
        else{
            auUrl=doOrdinaryUrl(url);
        }
        return auUrl;
    }

    private static AuUrl doOrdinaryUrl(String url){
        AuUrl auUrl=new AuUrl();
        auUrl.setUrl(BASE_URL +url+PATH_APPEND);
        return auUrl;
    }

    private static AuUrl doRoleUrl(String url){
        AuUrl auUrl=new AuUrl();
        String[] s=url.substring(1).split(ROLE_END);
        String[] roles=s[0].split(POWER_SEPARATOR);
        url= BASE_URL +s[1]+PATH_APPEND;
        if(!"".equals(roles[0]))
            auUrl.addRoles(Arrays.asList(roles));
        auUrl.setUrl(url);
        return auUrl;
    }

}
