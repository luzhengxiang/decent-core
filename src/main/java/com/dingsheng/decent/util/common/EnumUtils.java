package com.dingsheng.decent.util.common;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * 枚举类型工具类
 *
 * @author Lin.D.G
 *
 */
public class EnumUtils {
    /**
     * 获取指定枚举类型
     *
     * 通过指定枚举类型，和具体值（可以是name和ordinal）返回对应的枚举值
     *
     * @param type 指定枚举类型
     * @param v 指定枚举类型具体一个实现的name或ordinal
     * @return 返回指定枚举类型的一个具体实现，如果没有则是null
     */
    public static <T extends Enum<T>> T getEnum(Class<T> type, String v){
        Method m;
        try {
            m = type.getClass().getDeclaredMethod("enumConstantDirectory");

            if(m!=null){
                m.setAccessible(true);
                Map<String, T> map = (Map<String, T>) m.invoke(type);
                for ( Map.Entry< String, T> entry : map.entrySet() ) {
 //					System.out.println(String.format("%S->%S", k,map.get(k).ordinal()));
                    if(v.equalsIgnoreCase(StringUtil.isNumeric(v)?String.valueOf(entry.getValue().ordinal()):entry.getKey()))
                        return entry.getValue();
                }

            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }
}
