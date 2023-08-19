package com.jack.weChatSecurity.json;

import com.jack.weChatSecurity.utils.ReflectUtil;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 将不同源的数据转化为JsonValue
 */
public class JsonParser {

    /**
     * 将json字符串转化为JsonValue
     * @param json
     * @return JsonValue
     * @throws Exception
     */
    public JsonValue parseFromString(String json)throws Exception{
        return parse(new JsonToken(json));
    }

    protected JsonValue parse(JsonToken jsonToken)throws Exception{
        JsonValue value;
        String next = jsonToken.next();
        switch (next){
            case "{":{
                value=parseObject(jsonToken);
                break;
            }
            case "[":{
                value=parseArray(jsonToken);
                break;
            }
            default:{
                value=new JsonString(next);
            }
        }
        if (value==null)
            throw new JsonException("转化Json失败");
        return value;
    }

    private JsonObject parseObject(JsonToken jsonToken)throws Exception{
        Map<String,JsonValue> map=new HashMap<>();
        String key=null;
        JsonValue value;
        while (!jsonToken.isEnd()){
            String next= jsonToken.next();
            if (next.equals("}"))break;
            switch (next){
                case ":":{
                    String pre = jsonToken.pre();
                    key= pre.substring(1,pre.length()-1);
                    break;
                }
                case ",":{
                    key=null;
                    break;
                }
                case "{":
                case "[":
                default:{
                    if (key!=null) {
                        jsonToken.back();
                        value = parse(jsonToken);
                        map.put(key, value);
                        break;
                    }
                }
            }
        }
        return new JsonObject(map);
    }

    private JsonArray parseArray(JsonToken jsonToken)throws Exception{
        List<JsonValue> list=new LinkedList<>();
        while (!jsonToken.isEnd()) {
            JsonValue value;
            String next = jsonToken.next();
            if (next.equals("]"))break;
            switch (next) {
                case ",":{
                    break;
                }
                case "{":
                case "[":
                default:{
                    jsonToken.back();
                    value=parse(jsonToken);
                    list.add(value);
                    break;
                }
            }
        }
        return new JsonArray(list);
    }

    /**
     * 将对象转化为JsonValue
     * @param bean
     * @param <T>
     * @return JsonValue
     * @throws Exception
     */
    public <T> JsonValue parseFromBean(T bean)throws Exception{
        if (bean==null)return new JsonString("null");
        Class cls = bean.getClass();
        JsonValue value;
        if (cls==String.class)
            value=new JsonString("\""+bean+"\"");
        else if (cls==Short.class||cls==Integer.class||cls==Long.class||cls==Double.class||cls==Float.class||cls==Character.class||cls==Byte.class||cls==Boolean.class){
            value=new JsonString(String.valueOf(bean));
        }
        //数组类型componentType不为null的时候
        else if (cls.getComponentType()!=null){
            value=parseArray(bean);
        }
        else{
            value=parseObject(bean);
        }
        return value;
    }

    private <T> JsonObject parseObject(T obj)throws Exception{
        Map<String,JsonValue> map=new HashMap<>();
        Class<?> cls = obj.getClass();
        Field[] fields = cls.getDeclaredFields();
        for (Field field:fields){
            field.setAccessible(true);
            String name= field.getName();
            JsonValue value;
            if (List.class==field.getType()){
                List list=(List) field.get(obj);
                Class type = ReflectUtil.getGenericTypeFromCollection(field);
                value=parseFromBean(list.toArray((Object[]) Array.newInstance(type, 1)));
            }
            else value= parseFromBean(field.get(obj));
            map.put(name,value);
        }
        return new JsonObject(map);
    }

    private <T> JsonArray parseArray(T obj)throws Exception{
        List<JsonValue> list=new LinkedList<>();
        Class type = obj.getClass();
        if (type==short[].class){
            short[] ary=(short[])obj;
            for (short s:ary)
                list.add(parseFromBean(s));
        }
        else if (type==int[].class){
            int[] ary=(int[])obj;
            for (int i:ary)
                list.add(parseFromBean(i));
        }
        else if (type==long[].class){
            long[] ary=(long[])obj;
            for (long l:ary)
                list.add(parseFromBean(l));
        }
        else if (type==double[].class){
            double[] ary=(double[])obj;
            for (double d:ary)
                list.add(parseFromBean(d));
        }
        else if (type==float[].class){
            float[] ary=(float[])obj;
            for (float f:ary)
                list.add(parseFromBean(f));
        }
        else if (type==byte[].class){
            byte[] ary=(byte[])obj;
            for (byte b:ary)
                list.add(parseFromBean(b));
        }
        else if (type==boolean[].class){
            boolean[] ary=(boolean[])obj;
            for (boolean b:ary)
                list.add(parseFromBean(b));
        }
        else if (type==char[].class){
            char[] ary=(char[])obj;
            for (char c:ary)
                list.add(parseFromBean(c));
        }
        if (list.isEmpty()){
            Object[] ary=(Object[])obj;
            for (Object o:ary)
                list.add(parseFromBean(o));
        }
        return new JsonArray(list);
    }


}
