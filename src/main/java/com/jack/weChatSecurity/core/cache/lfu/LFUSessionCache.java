package com.jack.weChatSecurity.core.cache.lfu;

import com.jack.weChatSecurity.core.DefaultWeChatUser;
import com.jack.weChatSecurity.core.WeChatUser;
import com.jack.weChatSecurity.core.cache.CacheStrategy;
import com.jack.weChatSecurity.core.cache.SessionCacheBase;

import java.util.*;

public class LFUSessionCache extends SessionCacheBase<LFUSessionCache.Node> {
    class Node {
        private Node pre;
        private Node next;
        private int count;
        private String token;
        private WeChatUser weChatUser;

        public Node(int count,String token,WeChatUser weChatUser){
            this.token=token;
            this.weChatUser=weChatUser;
            this.count=count;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return count == node.count && pre.equals(node.pre) && next.equals(node.next) && token.equals(node.token) && weChatUser.equals(node.weChatUser);
        }

        @Override
        public int hashCode() {
            return Objects.hash(pre, next, count, token, weChatUser);
        }
    }
    //哨兵
    private final WeChatUser sentry=new DefaultWeChatUser();
    class LRU {
        private Node head;
        private Node tail;

        public LRU(Node node){
            int count = node.count;
            this.head=new Node(count,null,sentry);
            this.tail=new Node(count,null,sentry);
            this.head.next=node;
            node.pre=this.head;
            node.next=this.tail;
            this.tail.pre=node;
        }

        public void addNodeHead(Node node){
            node.next=this.head.next;
            this.head.next.pre=node;
            node.pre=this.head;
            this.head.next=node;
        }

        public Node getNodeTail(){
            return this.tail.pre;
        }

        public boolean isEmpty(){
            return this.head.weChatUser==this.head.next.weChatUser;
        }
    }

    //次数与LRU的映射
    private Map<Integer,LRU> lfu;
    //维持最小次数值的最小堆
    private PriorityQueue<Integer> min;

    public LFUSessionCache(CacheStrategy strategy,int capacity){
        super(strategy, capacity);
        this.lfu=new HashMap<>();
        this.min=new PriorityQueue<>();
    }

    private void addNode(Node node){
        int count = node.count;
        if (this.lfu.containsKey(count)){
            LRU lru = this.lfu.get(count);
            lru.addNodeHead(node);
        }
        else{
            LRU lru=new LRU(node);
            this.lfu.put(count,lru);
            this.min.add(count);
        }
    }

    private void unlink(Node node){
        node.pre.next=node.next;
        node.next.pre=node.pre;
        node.pre=null;
        node.next=null;
        //判断该次数对应的lru下是否还存在节点
        int count = node.count;
        LRU lru = this.lfu.get(count);
        //没节点则删除索引
        if (lru.isEmpty()){
            this.lfu.remove(count);
            this.min.remove(count);
        }
    }


    @Override
    public WeChatUser getSession(String key) {
        WeChatUser weChatUser=null;
        if (containsValue(key)) {
            Node node = getValue(key);
            weChatUser= node.weChatUser;
            //先从原链表断开
            unlink(node);
            //访问次数加一
            node.count++;
            //将该Node节点加到对应次数的链表头
            addNode(node);
        }
        return weChatUser;
    }

    @Override
    public void removeSession(String key) {
        if (containsValue(key)){
            Node node = removeValue(key);
            unlink(node);
        }
    }

    @Override
    public void putSession(String key, WeChatUser value) {
        if (containsValue(key)){
            Node node = getValue(key);
            unlink(node);
            removeValue(node.token);
        }
        if (isFull()){
            LRU lru = this.lfu.get(this.min.peek());
            if (!lru.isEmpty()){
                Node nodeTail = lru.getNodeTail();
                unlink(nodeTail);
                removeValue(nodeTail.token);
            }
        }
        Node node = new Node(1,key,value);
        addNode(node);
        putValue(key,node);
    }

    /**
     * 下面关于序列化
     */

    @Override
    public Map<String, Node> doLoadSessions(Map<String, WeChatUser> sessions) {
        Map<String,Node> map=new HashMap<>();
        ArrayList<Map.Entry<String, WeChatUser>> entries = new ArrayList<>(sessions.entrySet());
        //根据lastModifyTime排序，兼容其他类型转换为LFU
        Collections.sort(entries,(a,b)->{
            return (int)(a.getValue().getLastModifyTime()-b.getValue().getLastModifyTime());
        });
        for (Map.Entry<String,WeChatUser> entry:entries){
            String message = entry.getKey();
            WeChatUser value = entry.getValue();
            String[] split = message.split(LFU_SEPARATOR);
            String key=split[0];
            int count=Integer.valueOf(split[1]);
            Node node = new Node(count, key, value);
            //因为是头插所以lastModifyTime要从小到大
            addNode(node);
            map.put(key,node);
        }
        return map;
    }

    @Override
    public Map<String, WeChatUser> doSerializable(Map<String, Node> map) {
        Map<String,WeChatUser> result=new HashMap<>();
        for (Map.Entry<String,Node> entry: map.entrySet()){
            String key = entry.getKey();
            Node value = entry.getValue();
            //为了不引入多的或其他的序列化结构，将count信息粘入key中
            String message=key+LFU_SEPARATOR+value.count;
            result.put(message, value.weChatUser);
        }
        return result;
    }

}
