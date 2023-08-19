package com.jack.weChatSecurity.core.cache.fifo;

import com.jack.weChatSecurity.core.WeChatUser;
import com.jack.weChatSecurity.core.cache.CacheStrategy;
import com.jack.weChatSecurity.core.cache.SessionCacheBase;

import java.util.*;

public class FIFOSessionCache extends SessionCacheBase<WeChatUser> {
    private Queue<WeChatUser> sessions;

    public FIFOSessionCache(CacheStrategy strategy,int capacity){
        super(strategy, capacity);
        this.sessions=new LinkedList<>();
    }

    @Override
    public WeChatUser getSession(String key) {
        WeChatUser weChatUser=null;
        if (containsValue(key)){
            weChatUser = getValue(key);
        }
        return weChatUser;
    }

    @Override
    public void removeSession(String key) {
        if (isEmpty())return;
        this.sessions.remove(removeValue(key));
    }

    @Override
    public void putSession(String key, WeChatUser value) {
        if (containsValue(key)){
            this.sessions.remove(removeValue(key));
        }
        if (isFull()){
            WeChatUser poll = this.sessions.poll();
            String token = poll.getToken();
            removeSession(token);
        }
        putValue(key, value);
        this.sessions.add(value);
    }

    @Override
    public Map<String, WeChatUser> doLoadSessions(Map<String, WeChatUser> sessions) {
        WeChatUser[] weChatUsers = sessions.values().toArray(new WeChatUser[0]);
        Arrays.sort(weChatUsers,(a,b)->{
            return (int) (a.getRegisterTime()-b.getRegisterTime());
        });
        this.sessions=new LinkedList(Arrays.asList(weChatUsers));
        return sessions;
    }

    @Override
    public Map<String, WeChatUser> doSerializable(Map<String, WeChatUser> map) {
        return map;
    }
}
