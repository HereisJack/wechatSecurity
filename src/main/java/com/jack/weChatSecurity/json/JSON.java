package com.jack.weChatSecurity.json;

public final class JSON {
    private static JsonParser parser=new JsonParser();

    public static JsonObject readJsonObject(String json)throws Exception{
        return (JsonObject) parser.parseFromString(json);
    }

    public static <T> JsonObject readJsonObject(T obj)throws Exception{
        return (JsonObject) parser.parseFromBean(obj);
    }

    public static JsonArray readJsonArray(String json)throws Exception{
        return (JsonArray) parser.parseFromString(json);
    }

    public static <T> JsonArray readJsonArray(T ary)throws Exception{
        return (JsonArray) parser.parseFromBean(ary);
    }

    public static <T> T readBean(String json, Class<T> cls)throws Exception{
        return parser.parseFromString(json).toValue(cls);
    }

    public static <T> String writeString(T bean)throws Exception{
        String s = parser.parseFromBean(bean).toJsonString();
        return s.substring(0,s.length()-1);
    }

}
