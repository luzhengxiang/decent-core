package com.dingsheng.decent.util.redis;

/**
 * @luzhengxiang
 * @create 2020-04-05 14:51
 **/
public class RedisPropertyConfig {
    public static String get(String key){
        return RedisService.get(getKey(key));
    }
    public static void set(String key, String value){
        RedisService.set(getKey(key), value);
    }
    public static void remove(String key){
        RedisService.delKey(getKey(key));
    }
    public static boolean exists(String key){
        return RedisService.exists(getKey(key));
    }
    public static String getKey(String key){
        if(key.indexOf(RedisServerKeys.PROJECT_NAME)!=0)
            key = RedisServerKeys.PROJECT_NAME+key;
        return key;
    }
}
