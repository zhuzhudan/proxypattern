package com.study.pattern.proxy.jdkdynamicproxy;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class JdkProxy {
    public static final String ln = "\r\n";

    public static Object newProxyInstance(JdkClassLoader classLoader,
                                          Class<?>[] interfaces,
                                          JdkInvocationHandler h) {
        try {
            //1、动态生成源代码.java文件
            String src = generateSrc(interfaces);

            //2、Java文件输出磁盘
//            String filePath = JdkProxy.class.getResource("").getPath();
//            System.out.println(filePath);
            File file = new File(("E:\\$Proxy0.java"));

            FileWriter fw = new FileWriter(file);
            fw.write(src);
            fw.flush();
            fw.close();

            //3、把生成的.java文件编译成.class 文件
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            StandardJavaFileManager manager = compiler.getStandardFileManager(null,null,null);
            Iterable iterable = manager.getJavaFileObjects(file);

            JavaCompiler.CompilationTask task = compiler.getTask(null,manager,null,null,null,iterable);
            task.call();
            manager.close();

            //4、编译生成的.class文件加载到JVM中来
            Class proxyClass = classLoader.findClass("$Proxy0");
            Constructor constructor = proxyClass.getConstructor(JdkInvocationHandler.class);
            file.delete();

            //5、返回字节码重组以后的新的代理对象
            return constructor.newInstance(h);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String generateSrc(Class<?>[] interfaces){
        StringBuffer sb = new StringBuffer();

        for(Class<?> inface : interfaces){
            sb.append("package com.study.pattern.proxy.jdkdynamicproxy;" + ln);
            sb.append("import java.lang.reflect.Method;" + ln);
            sb.append("public class $Proxy0 implements " + inface.getName() + "{" + ln);
            sb.append("JdkInvocationHandler h;" + ln);
            sb.append("public $Proxy0(JdkInvocationHandler h) { " + ln);
            sb.append("this.h = h;" + ln);
            sb.append("}" + ln);
            for (Method method : inface.getMethods()) {
                Class<?>[] params = method.getParameterTypes();

                StringBuffer paramNames = new StringBuffer();
                StringBuffer paramValues = new StringBuffer();
                StringBuffer paramClasses = new StringBuffer();

                for(int i = 0; i < params.length; i++){
                    Class clazz = params[i];
                    String type = clazz.getName();
                    String paramName = toLowerFirstCase(clazz.getSimpleName());
                    paramNames.append(type + " " + paramName);
                    paramValues.append(paramName);
                    paramClasses.append(clazz.getName() + ".class");
                    if(i > 0 && i < params.length - 1){
                        paramClasses.append(",");
                        paramValues.append(",");
                        paramNames.append(",");
                    }
                }

                sb.append("public " + method.getReturnType().getName() + " " + method.getName() + "(" + paramNames.toString() + ") {" + ln);
                sb.append("try{" + ln);
                sb.append("Method m = " + inface.getName() + ".class.getMethod(\"" + method.getName() + "\", new Class[]{" + paramClasses.toString() + "});" + ln);
                sb.append(((method.getReturnType()==Void.class) ? "return " : "" )+ getCaseCode("this.h.invoke(this,m,new Object[]{"+paramValues+"})",method.getReturnType())+";" + ln);
                sb.append("} catch (Throwable e){" + ln);
                sb.append("e.printStackTrace();" + ln);
                sb.append("}" + ln);

                sb.append("}" + ln);
            }
            sb.append("}" + ln);
        }
        return sb.toString();
    }

    private static Map<Class,Class> mappings = new HashMap<Class, Class>();
    static {
        mappings.put(int.class,Integer.class);
    }

    private static String getReturnEmptyCode(Class<?> returnClass){
        if(mappings.containsKey(returnClass)){
            return "return 0;";
        }else if(returnClass == Void.class){
            return "";
        }else {
            return "return null;";
        }
    }

    private static String getCaseCode(String code, Class<?> returnClass){
        if(mappings.containsKey(returnClass)){
            return "((" + mappings.get(returnClass).getName() + ")" + code + ")." + returnClass.getSimpleName() + "Value()";
        }
        return code;
    }

    private static String toLowerFirstCase(String simpleName) {
        char[] chars = simpleName.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }

}
