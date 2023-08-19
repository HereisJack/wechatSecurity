package com.jack.weChatSecurity.utils;

import java.io.*;

public final class StreamUtil {

    /**
     * 将输入流的字节数据转换为字符串并返回
     * @param in
     * @param charset
     * @return
     * @throws IOException
     */
    public static String getStringFromStream(InputStream in,String charset)throws IOException {
        StringBuilder builder=new StringBuilder();
        BufferedReader reader=new BufferedReader(new InputStreamReader(in,charset));
        while(reader.ready()){
            builder.append(reader.readLine());
        }
        in.close();
        return builder.toString();
    }

    public static String getStringFromStream(InputStream in)throws IOException {
        return getStringFromStream(in,"utf-8");
    }


    /**
     * 流复制
     * @param in  输入流
     * @param out 输出流
     * @throws Exception
     */
    public static void copyStream(InputStream in, OutputStream out)throws Exception{
         int temp;
         byte[] content=new byte[1024];
         if(in!=null&&out!=null){
             while((temp=in.read(content))!=-1){
                 out.write(content,0, temp);
             }
         }
         in.close();
         out.close();
    }
}
