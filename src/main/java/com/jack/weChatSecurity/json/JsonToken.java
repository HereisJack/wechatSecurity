package com.jack.weChatSecurity.json;

import java.util.LinkedList;
import java.util.List;

public class JsonToken {
    private List<String> tokens;
    private int count;
    private int pre;

    // +1 JsonObject -1 JsonArray 0 JsonString
    private final int type;

    public JsonToken(String json){
        this.tokens=scan(json);
        this.type=tokens.get(0).equals("{")?1:(tokens.get(0).equals("[")?-1:0);
        this.count=0;
        this.pre=-2;
    }

    private List<String> scan(String source){
        char[] s=source.trim().toCharArray();
        List<String> tokens=new LinkedList<>();
        int i=0;
        while (i<s.length){
            switch (s[i]){
                case '{':
                case '}':
                case '[':
                case ']':
                case ',':
                case ':':{
                    tokens.add(String.valueOf(s[i++]));
                    break;
                }
                case 'n':
                case 't': {
                    char[] temp=new char[4];
                    for(int j=0;j<temp.length;j++){
                        temp[j]=s[i++];
                    }
                    tokens.add(new String(temp));
                    break;
                }
                case 'f':{
                    char[] temp=new char[5];
                    for(int j=0;j<temp.length;j++){
                        temp[j]=s[i++];
                    }
                    tokens.add(new String(temp));
                    break;
                }
                case '\'':
                case '"':{
                    StringBuilder builder=new StringBuilder();
                    builder.append('"');
                    i++;
                    while (i<s.length){
                        //转义字符
                        if(s[i]=='\\'){
                            builder.append(s[i++]);
                            builder.append(s[i++]);
                        }
                        if(s[i]=='"'||s[i]=='\''){
                            builder.append('"');
                            i++;
                            break;
                        }
                        builder.append(s[i++]);
                    }
                    tokens.add(builder.toString());
                    break;
                }
                default:{
                    if(s[i]==' '||s[i]=='\r'||s[i]=='\n'||s[i]=='\t'){
                        i++;
                        break;
                    }
                    StringBuilder builder=new StringBuilder();
                    while (s[i]>='0'&&s[i]<='9'||s[i]=='+'||s[i]=='-'||s[i]=='.'||s[i]=='e'||s[i]=='E'){
                        builder.append(s[i++]);
                    }
                    tokens.add(builder.toString());
                }
            }
        }
        return tokens;
    }

    public int getType(){return this.type;}

    public boolean isEnd(){
        return count>= tokens.size();
    }

    public String next()throws JsonException{
        if(isEnd()){
            throw new JsonException("关键字为空");
        }
        String s = tokens.get(count++);
        pre++;
        return s;
    }

    public String pre()throws JsonException{
        if (pre==-2){
            throw new JsonException("没有前继值");
        }
        return tokens.get(pre);
    }

    public void back()throws JsonException{
        if (count-1<0||pre-1<-2){
            throw new JsonException("不能回退");
        }
        count--;
        pre--;
    }
}
