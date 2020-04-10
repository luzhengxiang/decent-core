package com.dingsheng.decent.util.old;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * Cache工具类
 */
@Component
public class CacheUtils {

    private Map<String, String> dataMap = new HashMap<>();

    /**
     * 初始化
     */
    @PostConstruct
    public void init() {
    }

    /**
     * 查询
     * 如果数据没有缓存,那么从dataMap里面获取,如果缓存了,
     * 那么从guavaDemo里面获取
     * 并且将缓存的数据存入到 guavaDemo里面
     * 其中key 为 #id+dataMap
     */
    @Cacheable(value = "guavaDemo", key = "#id + 'dataMap'")
    public String GetCache(String id) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(sdf.format(new Date()) + " : query id is " + id);
        return dataMap.get(id);
    }

    /**
     * 插入 或者更新
     * 插入或更新数据到dataMap中
     * 并且缓存到 guavaDemo中
     * 如果存在了那么更新缓存中的值
     * 其中key 为 #id+dataMap
     */
    @CachePut(value = "guavaDemo", key = "#id + 'dataMap'")
    public String SetCache(String id, String value) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(sdf.format(new Date()) + " : add data ,id is " + id);
        dataMap.put(id, value);
        // data persistence
        return value;
    }

    /**
     * 删除
     * 删除dataMap里面的数据
     * 并且删除缓存guavaDemo中的数据
     * 其中key 为 #id+dataMap
     */
    @CacheEvict(value = "guavaDemo", key = "#id + 'dataMap'")
    public void remove(String id) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(sdf.format(new Date()) + " : remove id is " + id + " data");
        dataMap.remove(id);
        // data remove
    }

    /**
     * 短信验证码是否正确
     */
    public Boolean IsSmsCode(String key, String code) {
        String recode = GetCache(key);
        return recode != null && recode.equals(code);
    }

    public String Sms_BindPhone(String phone) {
        return "sms_bind_phone_" + phone;
    }

    public String Sms_ForgetPassword(String phone) {
        return "sms_forget_password_" + phone;
    }

    public String Sms_Register(String phone) {
        return "sms_register_" + phone;
    }

    public String Sms_UpdateLoginPwd(String phone) {
        return "sms_update_login_pwd" + phone;
    }

    public String Sms_UpdatePayPwd(String phone) {
        return "sms_update_pay_pwd" + phone;
    }
    public String Sms_USDT_Withdrawal(String phone) {
        return "sms_usdt_withdrawal" + phone;
    }
    public String Sms_Directional_Trade(String phone) {
        return "sms_directional_trade" + phone;
    }
    public String Sms_AU_Trade(String phone) {
        return "sms_au_trade" + phone;
    }
}

