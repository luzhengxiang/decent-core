package com.dingsheng.decent.intercepter;

import com.dingsheng.decent.common.ResponseData;
import com.dingsheng.decent.common.ResponseUtil;
import com.dingsheng.decent.constans.api.SysRetCodeConstants;
import com.dingsheng.decent.dto.api.CheckAuthRequest;
import com.dingsheng.decent.dto.api.CheckAuthResponse;
import com.dingsheng.decent.service.api.UserLoginService;
import com.dingsheng.decent.util.annotation.Anoymous;
import com.dingsheng.decent.util.core.CookieUtil;
import com.dingsheng.decent.util.core.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class TokenIntercepter extends HandlerInterceptorAdapter {


    @Autowired
    UserLoginService loginService;

    public static String ACCESS_TOKEN="access_token";

    public static String USER_INFO_KEY="userInfo";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod handlerMethod=(HandlerMethod)handler;
        Object bean=handlerMethod.getBean();
        if(isAnoymous(handlerMethod)){
            return true;
        }
        String token= CookieUtil.getCookieValue(request,ACCESS_TOKEN);
        if(StringUtils.isEmpty(token)){
            ResponseData responseData=new ResponseUtil<>().setErrorMsg("token已失效");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().write(JsonUtils.toString(responseData));
            return false;
        }

        CheckAuthRequest checkAuthRequest = new CheckAuthRequest();
        checkAuthRequest.setToken(token);
        CheckAuthResponse checkAuthResponse= loginService.validToken(checkAuthRequest);
        if(checkAuthResponse.getCode().equals(SysRetCodeConstants.SUCCESS.getCode())){
            request.setAttribute(USER_INFO_KEY,checkAuthResponse.getUserInfo()); //保存token解析后的信息后续要用
            return super.preHandle(request, response, handler);
        }

//        String userInfo = CookieUtil.validToken(token);
//        if(!StringUtil.isEmpty(userInfo)){
//            request.setAttribute(USER_INFO_KEY,userInfo); //保存token解析后的信息后续要用
//            return super.preHandle(request, response, handler);
//        }
//        ResponseData responseData=new ResponseUtil().setErrorMsg(checkAuthResponse.getMsg());

        ResponseData responseData=new ResponseUtil().setErrorMsg("登录失败");
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(JsonUtils.toString(responseData));
        return false;
    }

    private boolean isAnoymous(HandlerMethod handlerMethod){
        Object bean=handlerMethod.getBean();
        Class clazz=bean.getClass();
        if(clazz.getAnnotation(Anoymous.class)!=null){
            return true;
        }
        Method method=handlerMethod.getMethod();
        return method.getAnnotation(Anoymous.class)!=null;
    }
}
