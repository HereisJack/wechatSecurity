package com.jack.weChatSecurity.core.urlmatcher;

import com.jack.weChatSecurity.core.au.Request;
import com.jack.weChatSecurity.core.urlmatcher.urltrie.AuUrl;
import com.jack.weChatSecurity.core.urlmatcher.urltrie.Power;
import com.jack.weChatSecurity.core.urlmatcher.urltrie.UrlResolver;
import com.jack.weChatSecurity.core.urlmatcher.urltrie.UrlTrie;

import java.util.List;

public class UrlMatcherImpl implements UrlMatcher {
    private UrlTrie urlTrie;

    @Override
    public void init(List<String> urls) {
        this.urlTrie=new UrlTrie();
        for(AuUrl auUrl: UrlResolver.resolveUrl(urls))
            urlTrie.insertUrl(auUrl);
    }

    @Override
    public boolean matcher(Request request) {
        Power power = urlTrie.searchUrl(request.getUrl());
        if(power!=null) {
            request.setPower(power);
            return true;
        } else return false;
    }
}
