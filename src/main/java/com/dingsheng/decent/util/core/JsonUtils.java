package com.dingsheng.decent.util.core;

import com.google.gson.*;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @luzhengxiang
 * @create 2020-04-05 14:47
 **/
public class JsonUtils {
    private static final ConcurrentHashMap<String, Gson> gsons = new ConcurrentHashMap();

    public static Gson getGson(){
        return getGson("yyyy-MM-dd HH:mm:ss");
    }
    public static Gson getGson(String pattern){
        if(StringUtil.isEmpty(pattern)) return getGson();
        if(!gsons.containsKey(pattern)){
            Gson gson = new GsonBuilder().setDateFormat(pattern).create();
            gsons.put(pattern, gson);
        }
        return gsons.get(pattern);
    }
    public static String toString(Object o){
        return getGson().toJson(o);
    }
    public static String toString(Object o, String pattern){
        return getGson(pattern).toJson(o);
    }
    public static JsonObject toJsonObject(String o){
        if(StringUtil.isEmpty(o))return null;
        return new JsonParser().parse(o).getAsJsonObject();
    }
    public static JsonArray toJsonArray(String o){
        if(StringUtil.isEmpty(o))return null;
        return new JsonParser().parse(o).getAsJsonArray();
    }
    public static <T> T to(String s, Class<T> tt){
        return getGson().fromJson(s, tt);
    }
    public static <T> T to(String s, String pattern, Class<T> tt){
        return getGson(pattern).fromJson(s, tt);
    }
}
