package com.jack.weChatSecurity.core.cache;

import com.jack.weChatSecurity.core.WeChatUser;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public abstract class SessionCacheBase <V> implements SessionCache{
    protected static final String LFU_SEPARATOR="=";

    private CacheStrategy cacheStrategy;
    //这里的map为了方便序列化和更改缓存策略
    private Map<String, V> map;
    private int capacity;
    private int count;

    public SessionCacheBase(CacheStrategy strategy,int capacity){
        this.cacheStrategy=strategy;
        this.capacity=capacity;
        this.count=0;
        this.map=new HashMap<>();
    }

    protected boolean containsValue(String key){
        return this.map.containsKey(key);
    }

    protected V getValue(String key){
        return this.map.get(key);
    }

    protected V removeValue(String key){
        this.count--;
        return this.map.remove(key);
    }

    protected void putValue(String token, V weChatUser){
        this.map.put(token, weChatUser);
        this.count++;
    }

    protected Iterator<Map.Entry<String,V>> iterator(){
        return map.entrySet().iterator();
    }

    /**
     * 返回随机key
     * @return
     */
    protected String randomKey(){
        String[] strings = this.map.keySet().toArray(new String[0]);
        int index=(int)Math.random()* strings.length;
        return strings[index];
    }

    protected boolean isEmpty(){
        return this.count==0;
    }

    protected boolean isFull(){
        return this.count>=this.capacity;
    }

    @Override
    public int getCapacity() {
        return this.capacity;
    }

    @Override
    public CacheStrategy getCacheStrategy() {
        return this.cacheStrategy;
    }

    @Override
    public void loadSessions(Map<String, WeChatUser> sessions) {
        if (sessions!=null) {
            Iterator<String> iterator = sessions.keySet().iterator();
            if (iterator.hasNext()){
                String key = iterator.next();
                //下面都是为了兼容LFU向其他类型的转换（反之亦然）
                if (CacheStrategy.LFU!=this.cacheStrategy&&key.contains(LFU_SEPARATOR)){
                    Map<String,WeChatUser> after=new HashMap<>();
                    for (Map.Entry<String,WeChatUser> entry:sessions.entrySet()){
                        after.put(entry.getKey().split(LFU_SEPARATOR)[0],entry.getValue());
                    }
                    sessions=after;
                }
                if (CacheStrategy.LFU==this.cacheStrategy&&!key.contains(LFU_SEPARATOR)){
                    Map<String,WeChatUser> after=new HashMap<>();
                    for (Map.Entry<String,WeChatUser> entry:sessions.entrySet()){
                        after.put(entry.getKey()+LFU_SEPARATOR+1,entry.getValue());
                    }
                    sessions=after;
                }
            }
            Map<String, V> map = doLoadSessions(sessions);
            this.map=map;
            this.count= map.size();
        }
    }

    public abstract Map<String, V> doLoadSessions(Map<String, WeChatUser> sessions);

    @Override
    public Map<String, WeChatUser> serializable() {
        return doSerializable(this.map);
    }

    public abstract Map<String, WeChatUser> doSerializable(Map<String,V> map);
}
