package com.dingsheng.decent.controller.api;

import com.dingsheng.decent.common.ResponseData;
import com.dingsheng.decent.common.ResponseUtil;
import com.dingsheng.decent.common.exception.Assert;
import com.dingsheng.decent.constans.api.SysRetCodeConstants;
import com.dingsheng.decent.dto.api.UserLoginRequest;
import com.dingsheng.decent.dto.api.UserLoginResponse;
import com.dingsheng.decent.dto.api.user.RegisterRequest;
import com.dingsheng.decent.dto.api.user.SendSmsRequest;
import com.dingsheng.decent.entity.Member;
import com.dingsheng.decent.intercepter.TokenIntercepter;
import com.dingsheng.decent.service.api.UserLoginService;
import com.dingsheng.decent.service.api.user.UserService;
import com.dingsheng.decent.util.ExceptionUtil;
import com.dingsheng.decent.util.SmsUtils;
import com.dingsheng.decent.util.annotation.Anoymous;
import com.dingsheng.decent.util.core.CookieUtil;
import com.dingsheng.decent.util.old.CacheUtils;
import com.dingsheng.decent.util.old.EncryptUtil;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @luzhengxiang
 * @create 2020-04-05 23:10
 **/
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    UserLoginService userLoginService;

    @Autowired
    CacheUtils dataCache;

    /**
     *
     * @param telephone 手机号码
     * @param password 密码
     * @param code 验证码
     * @return
     */
    @ApiOperation(value = "登录", notes = "登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "telephone", value = "手机号码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "password", value = "输入密码,使用md5(明文 + maskCode)", required = true, dataType = "String"),
            @ApiImplicitParam(name = "code", value = "短信验证码", required = true, dataType = "String")
    })
    @Anoymous
    @GetMapping(value = "/login")
    public ResponseData login(String telephone, String password, String code,
                              HttpServletRequest request, HttpServletResponse response){
        UserLoginRequest loginRequest=new UserLoginRequest();
        loginRequest.setPassword(password);
        loginRequest.setUserName(telephone);

        //todo 验证验证码
        String captcha=code;

        UserLoginResponse userLoginResponse=userLoginService.login(loginRequest);
        if(userLoginResponse.getCode().equals(SysRetCodeConstants.SUCCESS.getCode())) {
            Cookie cookie= CookieUtil.genCookie(TokenIntercepter.ACCESS_TOKEN,userLoginResponse.getToken(),"/",24*60*60);
            cookie.setHttpOnly(true);
            response.addCookie(cookie);
            return new ResponseUtil<>().setData(userLoginResponse);
        }
        return new ResponseUtil().setErrorMsg(userLoginResponse.getMsg());
    }

    @ApiOperation(value = "登出", notes = "登出")
    @GetMapping(value = "/loginOut")
    public ResponseData loginOut(HttpServletRequest request, HttpServletResponse response){
        Cookie[] cookies = request.getCookies();
        if (null!=cookies) {
            for(Cookie cookie : cookies){
                if(cookie.getName().equals(TokenIntercepter.ACCESS_TOKEN)){
                    cookie.setValue(null);
                    cookie.setMaxAge(0);// 立即销毁cookie
                    cookie.setPath("/");
                    response.addCookie(cookie); //覆盖原来的token
                }
            }
        }
        return new ResponseUtil().setData(null);
    }

    @ApiOperation(value = "注册", notes = "注册")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "telephone", value = "手机号码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "loginPwd", value = "登录密码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "code", value = "短信验证码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "referralCode", value = "上级用户", required = true, dataType = "String"),
            @ApiImplicitParam(name = "payPwd", value = "支付密码", required = true, dataType = "String")
    })
    @Anoymous
    @GetMapping(value = "/register")
    public ResponseData register(RegisterRequest input, HttpServletRequest request, HttpServletResponse response){
        input.requestCheck();
        //暂时注释
//        if (!dataCache.IsSmsCode(dataCache.Sms_Register(input.getPhone()), input.getCode())) {
//            throw new BaseException(
//                    SysRetCodeConstants.KAPTCHA_CODE_ERROR.getCode(),
//                    SysRetCodeConstants.KAPTCHA_CODE_ERROR.getMessage());
//        }
        //注册逻辑
        userService.register(input);
        //清除缓存
        dataCache.remove(dataCache.Sms_Register(input.getPhone()));

        return new ResponseUtil().success();
    }


    @ApiOperation(value = "忘记密码", notes = "忘记密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "telephone", value = "手机号码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "code", value = "短信验证码", required = true, dataType = "String")
    })
    @Anoymous
    @GetMapping(value = "/forgetPwd")
    public ResponseData forgetPwd(){
        return new ResponseUtil().setData(null);
    }

    @ApiOperation(value = "修改密码", notes = "修改密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "telephone", value = "手机号码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "password", value = "新密码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "oldPassworPd", value = "原密码", required = true, dataType = "String")
    })
    @Anoymous
    @GetMapping(value = "/modifyPwd")
    public ResponseData modifyPwd(){


        return new ResponseUtil().setData(null);
    }

    @ApiOperation(value = "发送短信验证码", notes = "发送短信验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "手机号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "type", value = "短信类型  1=重置密码 2=注册 3=修改密码 4=修改二级密码", required = true, dataType = "int")
    })
    @PostMapping("sms")
    @Anoymous
    public ResponseData Sms(SendSmsRequest input) {
        input.requestCheck();
        try {
            input.requestCheck();
            String code = EncryptUtil.getInstance().GetRandNum(6, 1);
            //发送短信
            SmsUtils.Send(input.phone, code);
            switch (input.type) {
                case 1:
                    //忘记密码
                    dataCache.SetCache(dataCache.Sms_ForgetPassword(input.phone), code);
                    break;
                case 2:
                    //注册
                    dataCache.SetCache(dataCache.Sms_Register(input.phone), code);
                    break;
            }
        } catch (Exception ex) {
            ExceptionUtil.throwsBaseException("短信发送失败");
        }
        return new ResponseUtil().setData(null);
    }


    @ApiOperation(value = "获取用户基础信息（包含资产信息）", notes = "获取用户基础信息（包含资产信息）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户编号", required = true, dataType = "Long",paramType ="path")
    })
    @Anoymous
    @GetMapping(value = "/{id}")
    public ResponseData getInfo(@PathVariable Long id){
        Map map = new HashMap();
        Assert.isNull(id);
        map.put("account",userService.getAccount(id));
        map.put("member",userService.getMember(id));
        return new ResponseUtil().setData(map);
    }


    @ApiOperation(value = "获取用户下级用户", notes = "获取用户下级用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户编号", required = true, dataType = "Long",paramType ="path")
    })
    @Anoymous
    @GetMapping(value = "/{id}/getLower")
    public ResponseData getLower(@PathVariable Long id){
        List<Member> members = userService.getMyLower(id);
        return new ResponseUtil().setData(members);
    }

    @ApiOperation(value = "获取用户二级下级用户", notes = "获取用户下级用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户编号", required = true, dataType = "Long",paramType ="path")
    })
    @Anoymous
    @GetMapping(value = "/{id}/getSecondLower")
    public ResponseData getSecondLower(@PathVariable Long id){
        List<Member> members = userService.getMySecondLower(id);
        return new ResponseUtil().setData(members);
    }


    @ApiOperation(value = "获取资金流水", notes = "获取资金流水")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户编号", required = true, dataType = "Long",paramType ="path"),
            @ApiImplicitParam(defaultValue = "1", name = "page", value = "页数", paramType = "query"),
            @ApiImplicitParam(defaultValue = "100", name = "pageSize", value = "页面大小", paramType = "query")
    })
    @Anoymous
    @GetMapping(value = "/{id}/ioList")
    public ResponseData ioList(@PathVariable Long id,
                         @RequestParam(value = "page",defaultValue = "1",required = false)int page,
                         @RequestParam(value = "pageSize",defaultValue = "10",required = false)int pageSize){

        PageInfo pageInfo = userService.ioList(id,page,pageSize);
        return new ResponseUtil<>().setData(pageInfo);
    }




}
