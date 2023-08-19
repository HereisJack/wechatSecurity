package com.jack.weChatSecurity.json;

import java.lang.reflect.Array;
import java.util.List;

public class JsonArray implements JsonValue{
    private List<JsonValue> values;

    public JsonArray(List<JsonValue> list){
        this.values=list;
    }

    @Override
    public <T> T toValue(Class<T> bean) throws Exception {
        if (bean==short[].class){
            return (T)toShortArray();
        }
        if (bean==int[].class){
            return (T)toIntArray();
        }
        if (bean==long[].class){
            return (T)toLongArray();
        }
        if (bean==float[].class){
            return (T)toFloatArray();
        }
        if (bean==double[].class){
            return (T)toDoubleArray();
        }
        if (bean==byte[].class){
            return (T)toByteArray();
        }
        if (bean==boolean[].class){
            return (T)toBooleanArray();
        }
        if (bean==char[].class){
            return (T)toCharArray();
        }
        Class componentType = bean.getComponentType();
        Object[] ary = (Object[]) Array.newInstance(componentType, this.values.size());
        int i=0;
        for(JsonValue value:values){
            ary[i++]=value.toValue(bean.getComponentType());
        }
        return (T)ary;
    }

    private short[] toShortArray()throws Exception{
        short[] ary=new short[this.values.size()];
        int i=0;
        for(JsonValue value:values){
            ary[i++]=value.toValue(short.class);
        }
        return ary;
    }

    private int[] toIntArray()throws Exception{
        int[] ary=new int[this.values.size()];
        int i=0;
        for(JsonValue value:values){
            ary[i++]=value.toValue(int.class);
        }
        return ary;
    }

    private long[] toLongArray()throws Exception{
        long[] ary=new long[this.values.size()];
        int i=0;
        for(JsonValue value:values){
            ary[i++]=value.toValue(long.class);
        }
        return ary;
    }

    public float[] toFloatArray()throws Exception{
        float[] ary=new float[this.values.size()];
        int i=0;
        for(JsonValue value:values){
            ary[i++]=value.toValue(float.class);
        }
        return ary;
    }

    public double[] toDoubleArray()throws Exception{
        double[] ary=new double[this.values.size()];
        int i=0;
        for(JsonValue value:values){
            ary[i++]=value.toValue(double.class);
        }
        return ary;
    }

    public byte[] toByteArray()throws Exception{
        byte[] ary=new byte[this.values.size()];
        int i=0;
        for(JsonValue value:values){
            ary[i++]=value.toValue(byte.class);
        }
        return ary;
    }

    public boolean[] toBooleanArray()throws Exception{
        boolean[] ary=new boolean[this.values.size()];
        int i=0;
        for(JsonValue value:values){
            ary[i++]=value.toValue(boolean.class);
        }
        return ary;
    }

    public char[] toCharArray()throws Exception{
        char[] ary=new char[this.values.size()];
        int i=0;
        for(JsonValue value:values){
            ary[i++]=value.toValue(char.class);
        }
        return ary;
    }

    public boolean isEmpty(){
        return this.values.isEmpty();
    }

    @Override
    public String toJsonString() throws Exception {
        StringBuilder json=new StringBuilder();
        json.append("[");
        for(JsonValue jsonValue:values){
            json.append(jsonValue.toJsonString());
        }
        if (!isEmpty())
            json.deleteCharAt(json.lastIndexOf(","));
        json.append("],");
        return json.toString();
    }
}
