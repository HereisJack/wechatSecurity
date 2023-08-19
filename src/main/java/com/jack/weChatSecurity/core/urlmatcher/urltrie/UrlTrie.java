package com.jack.weChatSecurity.core.urlmatcher.urltrie;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class UrlTrie implements PathSeparator {
    private Map<String, UrlTrie> children;
    private Power power;
    private boolean isEnd;

    public UrlTrie(){
        this.children=new HashMap<>();
        this.power=new Power();
        this.isEnd =false;
    }

    public UrlTrie(Map<String,UrlTrie> children){
        this.children=children;
        this.isEnd=false;
    }

    public void insertUrl(AuUrl auUrl) {
        doInsertUrl(this,auUrl);
    }

    public Power searchUrl(String url) {
        return doSearchUrl(this,url+PATH_APPEND);
    }

    private void doInsertUrl(UrlTrie root, AuUrl auUrl){
        String url= auUrl.getUrl();
        if(url.equals(PATH_END)){
            root.isEnd =true;
            if(auUrl.getRoles()!=null) {
                root.power.addRoles(auUrl.getRoles());
            }
            return;
        }
        String[] s = url.split(PATH_SEPARATOR,2);
        String now =s[0];
        String next=s[1];
        auUrl.setUrl(next);
        if(!root.children.containsKey(now)){
            root.children.put(now,new UrlTrie());
        }
        doInsertUrl(root.children.get(now),auUrl);
    }

    private Power doSearchUrl(UrlTrie root, String url){
        Power power=null;
        if(url.equals(PATH_END)){
            if(root.isEnd){
                power= root.power;
            }
            return power;
        }
        String[] s = url.split(PATH_SEPARATOR,2);
        String now =s[0];
        String next=s[1];
        if(root.children.containsKey(now)) {
            power = doSearchUrl(root.children.get(now), next);
            if(power==null){
                power=doSearchUrl(root.children.get(now),PATH_WILDCARD+PATH_APPEND);
            }
        }
        return power;
    }

    @Override
    public int hashCode() {
        return Objects.hash(children, isEnd);
    }

    @Override
    public boolean equals(Object obj) {
        if(this==obj)return true;
        else return false;
    }
}
