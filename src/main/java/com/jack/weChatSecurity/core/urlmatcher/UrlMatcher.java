package com.jack.weChatSecurity.core.urlmatcher;

import com.jack.weChatSecurity.core.au.Request;

import java.util.List;

public interface UrlMatcher {

    void init(List<String> urls);

    boolean matcher(Request request);

}
