package com.dingsheng.decent.constans.admin.system;

public class CookieName {
    public static String USER_OPENID = "user_openId";
    public static String USER_ID = "user_id";

    public static String SMS_TOTAL = "sms_total";
    public static String SMS_PREV = "sms_prev";

    public static String Admin_Login = "admin_login";
    public static String Admin_LOGIN_VALIDATE_CODE = "admin_code";


    public static String Sms_Login(String phone) {
        return "sms_login_" + phone;
    }
    public static String Sms_Register(String phone) {
        return "sms_register_" + phone;
    }
    public static String Sms_UpdatePwd(String phone) {
        return "sms_updatePwd_" + phone;
    }

    public static String API_Register = "api_register";
}
