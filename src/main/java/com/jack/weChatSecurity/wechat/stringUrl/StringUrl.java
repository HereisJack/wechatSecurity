package com.jack.weChatSecurity.wechat.stringUrl;

import com.jack.weChatSecurity.utils.StreamUtil;

import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class StringUrl implements UrlSeparator{

    private PROTOCOL protocol=PROTOCOL.HTTP;
    private REQUEST_METHOD method=REQUEST_METHOD.GET;
    private String host="localhost";
    private int port=80;
    private String path="/";
    private Map<String,String> params;

    public StringUrl addProtocol(PROTOCOL protocol) {
        this.protocol=protocol;
        return this;
    }

    public StringUrl addRequestMethod(REQUEST_METHOD method) {
        this.method=method;
        return this;
    }

    public StringUrl addHost(String host) {
        this.host=host;
        return this;
    }

    public StringUrl addPort(int port) {
        this.port=port;
        return this;
    }

    public StringUrl addQueryPath(String path) {
        this.path=path;
        return this;
    }

    public StringUrl addParams(Map<String, String> params) {
        this.params=params;
        return this;
    }

    public String connect() throws Exception {
        String url=wrapUrl();
        String json="";
        if(REQUEST_METHOD.GET==method){
            json=doGet(url);
        }
        if(REQUEST_METHOD.POST==method){
            json=doPost(url);
        }
        return json;
    }

    private String wrapUrl(){
        StringBuilder builder=new StringBuilder();
        builder.append(protocol.value());
        builder.append(HOST_SEPARATOR);
        builder.append(host);
        builder.append(PORT_SEPARATOR);
        builder.append(port);
        builder.append(path);
        return builder.toString();
    }

    private String doGet(String url)throws Exception{
        StringBuilder builder=new StringBuilder(url);
        if(params!=null){
            builder.append(GET_QUERY);
            builder.append(loadParam());
        }
        HttpURLConnection connection = (HttpURLConnection) new URL(builder.toString()).openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
        return StreamUtil.getStringFromStream(connection.getInputStream());
    }
    private String doPost(String url)throws Exception{
        String body="";
        if(params!=null){
            body=loadParam();
        }
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded;charset=utf-8");
        connection.setDoOutput(true);
        connection.getOutputStream().write(body.getBytes(StandardCharsets.UTF_8));
        connection.connect();
        return StreamUtil.getStringFromStream(connection.getInputStream());
    }
    private String loadParam(){
        StringBuilder builder=new StringBuilder();
        for(Map.Entry<String,String> entry: params.entrySet()){
            String key= entry.getKey();
            String value= entry.getValue();
            builder.append(key);
            builder.append(EQUAL);
            builder.append(value);
            builder.append(GET_AND);
        }
        builder.deleteCharAt(builder.lastIndexOf(GET_AND));
        return builder.toString();
    }
}
