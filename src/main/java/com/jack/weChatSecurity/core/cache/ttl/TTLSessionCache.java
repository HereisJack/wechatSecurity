package com.jack.weChatSecurity.core.cache.ttl;

import com.jack.weChatSecurity.core.WeChatUser;
import com.jack.weChatSecurity.core.cache.CacheStrategy;
import com.jack.weChatSecurity.core.cache.SessionCacheBase;

import java.sql.Timestamp;
import java.util.Map;

public class TTLSessionCache extends SessionCacheBase<WeChatUser> {
    private final int spin=5;
    private final int sample=10;
    private long ttlTime;

    public TTLSessionCache(CacheStrategy cacheStrategy,int capacity){
        super(cacheStrategy,capacity);
    }

    public void setTtlTime(long ttlTime) {
        this.ttlTime = ttlTime;
    }

    public long getTtlTime(){
        return this.ttlTime;
    }

    @Override
    public WeChatUser getSession(String key) {
        WeChatUser weChatUser=null;
        if (containsValue(key)) {
            weChatUser = getValue(key);
        }
        return weChatUser;
    }

    @Override
    public void removeSession(String key) {
        if (isEmpty())return;
        removeValue(key);
    }

    @Override
    public void putSession(String key, WeChatUser value) {
        if (containsValue(key))
            removeValue(key);
        int count=0;
        //如果满了，自旋等待TTLMonitor清理
        while (isFull()&&count++<spin){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //自旋成功则保存
        if (!isFull())
            putValue(key, value);
    }

    @Override
    public Map<String, WeChatUser> doLoadSessions(Map<String, WeChatUser> sessions) {
        return sessions;
    }

    @Override
    public Map<String, WeChatUser> doSerializable(Map<String, WeChatUser> map) {
        return map;
    }

    /**
     * ttl淘汰缓存session
     */
    public void ttlSessions() {
        //随机取sample个样本查看是否过期
        //这里采用随机的理由是当缓存
        for (int i = 0; !isEmpty()&&i < sample; i++) {
            String randomKey = randomKey();
            WeChatUser session = getSession(randomKey);
            Timestamp expect = new Timestamp(session.getRegisterTime() + this.ttlTime);
            Timestamp now = new Timestamp(System.currentTimeMillis());
            if (now.after(expect)) {
                removeValue(randomKey);
                System.out.println("remove!!");
            }
        }
    }
}
