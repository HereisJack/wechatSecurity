package com.jack.weChatSecurity.json;

import com.jack.weChatSecurity.utils.ReflectUtil;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class JsonObject implements JsonValue{
    private Map<String,JsonValue> key_value;

    public JsonObject(Map<String,JsonValue> map){
        this.key_value=map;
    }

    @Override
    public <T> T toValue(Class<T> bean) throws Exception {
        T t = ReflectUtil.newInstance(bean);
        for(Map.Entry<String,JsonValue> entry: key_value.entrySet()){
            String name=entry.getKey();
            JsonValue jsonValue=entry.getValue();
            Field field = bean.getDeclaredField(name);
            field.setAccessible(true);
            Object value=null;
            if (List.class.isAssignableFrom(field.getType())){
                Class collectionType = getListType(field);
                Object[] ary=(Object[])jsonValue.toValue(collectionType);
                if (ary!=null)
                    value= Arrays.asList(ary);
            }
            else value=jsonValue.toValue(field.getType());
            field.set(t,value);
        }
        return t;
    }

    public boolean isEmpty(){
        return key_value.isEmpty();
    }

    /**
     * 支持List代替数组,查找List泛型里的类型
     * @param field List字段
     * @return 数据类型
     * @throws JsonException
     */
    private Class getListType(Field field)throws JsonException{
        Class arrayType;
        try {
            arrayType=ReflectUtil.getGenericTypeFromCollection(field);
        } catch (Exception e){
            throw new JsonException("不支持嵌套List,考虑二维数值\n\r"+e.getMessage());
        }
        return Array.newInstance(arrayType, 1).getClass();
    }

    @Override
    public String toJsonString() throws Exception {
        StringBuilder json=new StringBuilder();
        json.append("{");
        for (Map.Entry<String,JsonValue> entry: key_value.entrySet()){
            String name=entry.getKey();
            JsonValue jsonValue=entry.getValue();
            json.append("\""+name+"\":");
            json.append(jsonValue.toJsonString());
        }
        if (!isEmpty())
            json.deleteCharAt(json.lastIndexOf(","));
        json.append("},");
        return json.toString();
    }
}
