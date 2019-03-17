package com.study.pattern.proxy.jdkdynamicproxy;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class JdkClassLoader extends ClassLoader {
    private File classPathFile;

    public JdkClassLoader(){
        String classPath = "E:\\";//JdkClassLoader.class.getResource("").getPath();
        this.classPathFile = new File(classPath);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String className = JdkClassLoader.class.getPackage().getName() + "." + name;
        if(classPathFile != null){
            File classFile = new File(classPathFile, name.replaceAll("\\.", "/") + ".class");
            if(classFile.exists()){
                FileInputStream inStream = null;
                ByteArrayOutputStream outStream = null;

                try{
                    inStream = new FileInputStream(classFile);
                    outStream = new ByteArrayOutputStream();
                    byte[] buff = new byte[1024];
                    int len;
                    while ((len = inStream.read(buff)) != -1){
                        outStream.write(buff, 0, len);
                    }
                    return defineClass(className, outStream.toByteArray(), 0, outStream.size());
                } catch (Exception e){
                    e.printStackTrace();
                } finally {
                    if(null != inStream){
                        try{
                            inStream.close();
                        } catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                    if(null != outStream){
                        try {
                            outStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return null;
    }
}
