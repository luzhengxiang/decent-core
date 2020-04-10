package com.dingsheng.decent.util.common;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/4.
 */
public class MapUtils {

    public static Map<String, String> getParameters(HttpServletRequest request) {
        Map<String, String> params = new HashMap<String, String>();
        Enumeration<String> enumeration = request.getParameterNames();
        while (enumeration.hasMoreElements()) {
            String parameter = enumeration.nextElement().toString();
            String[] parameterValues = request.getParameterValues(parameter);
            params.put(parameter, StringUtil.join(parameterValues, ","));
        }
        return params;
    }
}
