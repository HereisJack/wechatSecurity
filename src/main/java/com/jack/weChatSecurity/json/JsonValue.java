package com.jack.weChatSecurity.json;

public interface JsonValue {

    /**
     * 将JsonValue装载进type里面
     * @param type 装载的类对象
     * @param <T>
     * @return 类实例
     * @throws Exception
     */
    public <T> T toValue(Class<T> type)throws Exception;

    /**
     * 将JsonValue输出为Json字符串
     * @return Json字符串
     * @throws Exception
     */
    public String toJsonString()throws Exception;

}
