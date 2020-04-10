package com.dingsheng.decent.util.core;

import com.dingsheng.decent.util.encrypt.Blowfish;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.Base64UrlCodec;
import lombok.Builder;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * @luzhengxiang
 * @create 2020-04-06 13:51
 **/
@Slf4j
@Builder
public class JwtTokenUtils {

    private static final String COOKIEKEY = "#@$%$^%2&&*&8(*)a";//"aSD9FGE16GWR1Erhfh3jg12vasdfj";

    private static final String version = "1";

    /**
     * 传输信息，必须是json格式
     */
    private String msg;
    /**
     * 所验证的jwt
     */
    @Setter
    private String token;

    private final String secret="324iu23094u598ndsofhsiufhaf_+0wq-42q421jiosadiusadiasd";

    public String creatJwtToken () {
        Map<String, Object> m = new HashMap<>();
        m.put("userInfo",m);
        return Jwts.builder().setClaims(m)
                //.setId(uid).setSubject(userInfo).setAudience(nick).set().setSubject(userInfo)
                .signWith(SignatureAlgorithm.HS256, Base64UrlCodec.BASE64.encode(COOKIEKEY + version)).compact();
    }
    /**
     * 解密jwt并验证是否正确
     */
    public String freeJwt () {

        if(!StringUtil.isEmpty(token)){
            try {
                Claims claims = Jwts.parser().setSigningKey(Base64UrlCodec.BASE64.encode(COOKIEKEY + version)).parseClaimsJws(token).getBody();
//			String userInfo = claims.getSubject();
                String userInfo = claims.get("userInfo", String.class);
                return Blowfish.decode(userInfo);
            }catch (Exception e){
                log.info(String.format("JWT解析token失败：%s【%s】",e.getMessage(), token));
//                markLogout(getResponse());
            }
        }
        return null;

    }
}
