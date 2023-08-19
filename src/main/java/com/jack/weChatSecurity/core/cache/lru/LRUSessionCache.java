package com.jack.weChatSecurity.core.cache.lru;

import com.jack.weChatSecurity.core.WeChatUser;
import com.jack.weChatSecurity.core.cache.CacheStrategy;
import com.jack.weChatSecurity.core.cache.SessionCacheBase;

import java.util.*;

public class LRUSessionCache extends SessionCacheBase<LRUSessionCache.Node> {
    class Node {
        private Node pre;
        private Node next;
        private String token;
        private WeChatUser weChatUser;

        public Node(){}

        public Node(String token,WeChatUser weChatUser){
            this.token=token;
            this.weChatUser=weChatUser;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return pre.equals(node.pre) && next.equals(node.next) && token.equals(node.token) && weChatUser.equals(node.weChatUser);
        }

        @Override
        public int hashCode() {
            return Objects.hash(pre, next, token, weChatUser);
        }
    }

    private Node head;
    private Node tail;

    private final Node headSentry = new Node();
    private final Node tailSentry = new Node();

    public LRUSessionCache(CacheStrategy strategy,int capacity){
        super(strategy, capacity);
        this.head=headSentry;
        this.tail=tailSentry;
        this.head.next=this.tail;
        this.tail.pre=this.head;
    }

    private void addNodeHead(Node node){
        node.pre=head;
        node.next=head.next;
        head.next.pre=node;
        head.next=node;
    }

    private void unlinkNode(Node node){
        node.pre.next=node.next;
        node.next.pre=node.pre;
    }

    @Override
    public WeChatUser getSession(String key) {
        WeChatUser weChatUser = null;
        if (containsValue(key)){
            Node value = getValue(key);
            weChatUser = value.weChatUser;
            unlinkNode(value);
            addNodeHead(value);
        }
        return weChatUser;
    }

    @Override
    public void removeSession(String key) {
        if (containsValue(key)){
            Node node = removeValue(key);
            unlinkNode(node);
        }
    }

    @Override
    public void putSession(String key, WeChatUser value) {
        if (containsValue(key)){
            unlinkNode(removeValue(key));
        }
        if (isFull()){
            Node node = removeValue(tail.pre.token);
            unlinkNode(node);
        }
        Node node = new Node(key, value);
        addNodeHead(node);
        putValue(key,node);
    }

    @Override
    public Map<String, Node> doLoadSessions(Map<String, WeChatUser> sessions) {
        Map<String,Node> nodeMap=new HashMap<>();
        WeChatUser[] weChatUsers = sessions.values().toArray(new WeChatUser[0]);
        //根据lastModifyTime排序，兼容其他类型转换位LRU
        Arrays.sort(weChatUsers,(a,b)->(int)(a.getLastModifyTime()-b.getLastModifyTime()));
        for (WeChatUser weChatUser:weChatUsers) {
            Node node = new Node(weChatUser.getToken(), weChatUser);
            //因为是头插所以lastModifyTime要从小到大
            addNodeHead(node);
            nodeMap.put(weChatUser.getToken(), node);
        }
        return nodeMap;
    }

    @Override
    public Map<String, WeChatUser> doSerializable(Map<String, Node> map) {
        Map<String,WeChatUser> serializable=new HashMap<>();
        Collection<Node> values = map.values();
        for (Node node:values)
            serializable.put(node.token,node.weChatUser);
        return serializable;
    }
}
