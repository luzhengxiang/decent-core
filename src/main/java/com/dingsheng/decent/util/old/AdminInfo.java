package com.dingsheng.decent.util.old;

import com.dingsheng.decent.entity.SysAdmin;

public class AdminInfo {
    public static SysAdmin getPAdmin() {
        return PAdmin;
    }

    public static void setPadmin(SysAdmin pAdmin) {
        PAdmin = pAdmin;
    }

    static SysAdmin PAdmin;
}
