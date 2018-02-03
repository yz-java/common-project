package com.yz.common.core.utils;


import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.Modifier;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;
import org.springframework.util.ClassUtils;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * Created by yangzhao on 17/8/2.
 */
public class ClassUtil extends ClassUtils {

    /**
     * 对象属性解析生成对应类型的值
     * @param tClass
     * @param v
     * @param <T>
     * @return
     */
    public static  <T> T parse(Class<T> tClass,Object v){
        String simpleName = tClass.getSimpleName();
        if (simpleName.equals("int")||simpleName.equals("Integer")){
            return (T) Integer.valueOf(v.toString());
        }
        if (simpleName.equals("long")||simpleName.equals("Long")){
            return (T) Long.valueOf(v.toString());
        }
        if (simpleName.equals("String")){
            return (T) v.toString();
        }
        if (simpleName.equals("double")||simpleName.equals("Double")){
            return (T) Double.valueOf(v.toString());
        }
        if (simpleName.equals("Date")){
            try {
                long l = Long.parseLong(v.toString());
                return (T) new Date(l);
            }catch (Exception e){
                return (T) v;
            }
        }
        return null;
    }

    /**
     * 生成方法名称
     * @param symbol 在属性名前添加的标识符
     * @param fieldName 属性名
     * @return
     */
    public static String createFieldMethodName(String symbol,String fieldName){
        int length = fieldName.length();
        if (length==1){
            return symbol+fieldName.toUpperCase();
        }
        String substring = fieldName.substring(1, 2);
        char c = substring.charAt(0);
        if (Character.isUpperCase(c)){
            return symbol+fieldName;
        }
        String s = StringUtils.firstCharToUpper(fieldName);
        return symbol+s;
    }

    /**
     * 基于javassist获取类方法参数名称
     * @param method
     * @return
     * @throws Exception
     */
    public static String[] getMethodParamsName(Method method) throws Exception{
        Class<?> clazz = method.getDeclaringClass();
        ClassPool pool = ClassPool.getDefault();
        CtClass clz = pool.get(clazz.getName());
        CtClass[] params = new CtClass[method.getParameterTypes().length];
        for (int i = 0; i < method.getParameterTypes().length; i++) {
            params[i] = pool.getCtClass(method.getParameterTypes()[i].getName());
        }
        CtMethod cm = clz.getDeclaredMethod(method.getName(), params);
        MethodInfo methodInfo = cm.getMethodInfo();
        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
        if (codeAttribute==null)
            return null;
        LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute
                .getAttribute(LocalVariableAttribute.tag);
        int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
        String[] paramNames = new String[cm.getParameterTypes().length];
        if (attr==null)
            return null;
        for (int i = 0; i < paramNames.length; i++) {
            paramNames[i] = attr.variableName(i + pos);
        }
        return paramNames;
    }

}
