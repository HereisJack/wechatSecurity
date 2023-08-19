package com.jack.weChatSecurity.utils;

import java.io.File;
import java.net.URL;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

public final class ClassUtil {

    public static List<Class> getClassByPackage(String packagePath)throws Exception{
        List<Class> list=new LinkedList<>();
        Enumeration<URL> resources = Thread.currentThread().getContextClassLoader().getResources(packagePath.replaceAll("\\.", "/"));
        while (resources.hasMoreElements()){
            URL url= resources.nextElement();
            if("file".equals(url.getProtocol())){
                File file=new File(url.getFile());
                if (file.isDirectory()){
                    findDir(packagePath,file, list);
                }
                if (file.isFile()){
                    addFile(packagePath,file,list);
                }
            }
        }
        return list;
    }

    private static void findDir(String packagePath,File file,List<Class> list)throws Exception{
        File[] files = file.listFiles();
        for(File f:files){
            if(f.isDirectory()) {
                String name=f.getName();
                findDir(packagePath+"."+name,f, list);
            }
            if (f.isFile()){
                addFile(packagePath,f,list);
            }
        }
    }

    private static void addFile(String packagePath,File file,List<Class> list)throws Exception{
        String name=file.getName();
        String[] s;
        if ((s=name.split("\\.")).length>1&&"class".equals(s[1])){
            String path =packagePath + "." + s[0];
            list.add(ReflectUtil.loadClass(path));
        }
    }
}
