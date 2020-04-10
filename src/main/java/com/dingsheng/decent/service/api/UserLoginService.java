package com.dingsheng.decent.service.api;

import com.dingsheng.decent.constans.api.SysRetCodeConstants;
import com.dingsheng.decent.dto.api.CheckAuthRequest;
import com.dingsheng.decent.dto.api.CheckAuthResponse;
import com.dingsheng.decent.dto.api.UserLoginRequest;
import com.dingsheng.decent.dto.api.UserLoginResponse;
import com.dingsheng.decent.entity.Member;
import com.dingsheng.decent.mapper.MemberMapper;
import com.dingsheng.decent.util.ExceptionProcessorUtils;
import com.dingsheng.decent.util.core.JsonUtils;
import com.dingsheng.decent.util.core.JwtTokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @luzhengxiang
 * @create 2020-04-07 16:03
 **/
@Service
@Slf4j
public class UserLoginService {

    @Autowired
    MemberMapper memberMapper;

    public UserLoginResponse login(UserLoginRequest request) {
        log.info("Begin UserLoginServiceImpl.login: request:"+request);
        UserLoginResponse response=new UserLoginResponse();
        try {
            request.requestCheck();
            Example example = new Example(Member.class);
            example.createCriteria().andEqualTo("telephone",request.getUserName());
            List<Member> members = memberMapper.selectByExample(example);

            if(members==null||members.size()==0) {
                response.setCode(SysRetCodeConstants.USERORPASSWORD_ERRROR.getCode());
                response.setMsg(SysRetCodeConstants.USERORPASSWORD_ERRROR.getMessage());
                return response;
            }
            //验证是否已经激活
//            if("N".equals(member.get(0).getIsVerified())){
//                response.setCode(SysRetCodeConstants.USERORPASSWORD_ERRROR.getCode());
//                response.setMsg(SysRetCodeConstants.USERORPASSWORD_ERRROR.getMessage());
//                return response;
//            }
            if(!DigestUtils.md5DigestAsHex(request.getPassword().getBytes()).equals(members.get(0).getPwd())){
                response.setCode(SysRetCodeConstants.USERORPASSWORD_ERRROR.getCode());
                response.setMsg(SysRetCodeConstants.USERORPASSWORD_ERRROR.getMessage());
                return response;
            }
            Map<String,Object> map=new HashMap<>();
            map.put("id",members.get(0).getId());

            String token=JwtTokenUtils.builder().msg(JsonUtils.toString(map)).build().creatJwtToken();
//            response=UserConverterMapper.INSTANCE.converter(member.get(0));
            response.setToken(token);
            response.setCode(SysRetCodeConstants.SUCCESS.getCode());
            response.setMsg(SysRetCodeConstants.SUCCESS.getMessage());
        }catch (Exception e){
            e.printStackTrace();
            log.error("UserLoginServiceImpl.login Occur Exception :"+e);
            ExceptionProcessorUtils.wrapperHandlerException(response,e);
        }
        return response;
    }


    public CheckAuthResponse validToken(CheckAuthRequest request){
        log.info("Begin UserLoginService.validToken: request:"+request);
        CheckAuthResponse response=new CheckAuthResponse();
        response.setCode(SysRetCodeConstants.SUCCESS.getCode());
        response.setMsg(SysRetCodeConstants.SUCCESS.getMessage());
        try{
            request.requestCheck();
            String decodeMsg= JwtTokenUtils.builder().token(request.getToken()).build().freeJwt();
            if(StringUtils.isNotBlank(decodeMsg)){
                log.info("validate success");
                response.setUserInfo(decodeMsg);
                return response;
            }
            response.setCode(SysRetCodeConstants.TOKEN_VALID_FAILED.getCode());
            response.setMsg(SysRetCodeConstants.TOKEN_VALID_FAILED.getMessage());
        }catch (Exception e){
            log.error("UserLoginService.validToken Occur Exception :"+e);
            ExceptionProcessorUtils.wrapperHandlerException(response,e);
        }
        return response;
    }


}
