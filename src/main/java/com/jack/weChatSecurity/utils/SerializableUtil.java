package com.jack.weChatSecurity.utils;

import java.io.*;

/**
 * 序列化工具类
 */
public final class SerializableUtil {

    /**
     * 从文件流序列化到对象
     * @param file
     * @return
     * @throws Exception
     */
    public static Object readFromFile(File file)throws Exception {
        Object obj=null;
        if(file.exists()){
            try (ObjectInputStream in=new ObjectInputStream(new FileInputStream(file))){
                obj = in.readObject();
            }
        }
        return obj;
    }

    /**
     * 将对象序列化到文件
     * @param file
     * @param obj
     * @throws Exception
     */
    public static void writeToFile(File file,Object obj)throws Exception{
        if (!file.exists()){
            file.createNewFile();
        }
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
        out.writeObject(obj);
        out.close();
    }
}
