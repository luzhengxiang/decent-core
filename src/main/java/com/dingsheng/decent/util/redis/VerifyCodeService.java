package com.dingsheng.decent.util.redis;

import com.dingsheng.decent.util.common.Common;
import com.dingsheng.decent.util.core.PropertyConfig;
import com.dingsheng.decent.util.core.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 临时实现，日后可使用nosql产品代替
 * @author lin
 *
 */
public class VerifyCodeService {
	private static Logger logger = LoggerFactory.getLogger(VerifyCodeService.class);
	
	private static Map<String,String> vc = new HashMap<String, String>();
	
	private static String SUBFIX_TELEPHONE="MO:";
	private static String SUBFIX_EMAIL="EM:";
	private static String SUBFIX_NORMAL="NOR:";
	//短信验证码长度
	public final static int verifyCodeLength = StringUtil.getNumberDefault(
			PropertyConfig.getPropertyValue("sms.VerifyCode.length"), "6");


	public static boolean isTeletephoneVerifyCodeExpired(String key){
		return System.currentTimeMillis()-getExpired(SUBFIX_TELEPHONE+key)>=0L;
	}
	public static boolean isEmailVerifyCodeExpired(String key){
		return System.currentTimeMillis()-getExpired(SUBFIX_EMAIL+key)>=0;
	}
	public static boolean isCodeExpired(String key){
		return System.currentTimeMillis()-getExpired(SUBFIX_NORMAL+key)>=0;
	}
	public static boolean checkTelephoneVerifyCode(String key,String value){
		removeExpired();
		return StringUtil.isEmpty(value)?false:value.equals(getTelephoneVerifyCode(key));
	}
	public static boolean checkEmailVerifyCode(String key,String value){
		removeExpired();
		return StringUtil.isEmpty(value)?false:value.equals(getEmailVerifyCode(key));
	}
	public static boolean checkCode(String key,String value){
		removeExpired();
		return StringUtil.isEmpty(value)?false:value.equals(getCode(key));
	}
	public static String getTelephoneVerifyCode(String key){
		removeExpired();
		return getVerifyCode(SUBFIX_TELEPHONE, key);
	}
	public static String getEmailVerifyCode(String key){
		removeExpired();
		return getVerifyCode(SUBFIX_EMAIL, key);
	}
	public static String getCode(String key){
		removeExpired();
		return getVerifyCode(SUBFIX_NORMAL, key);
	}
	public static String regTelephoneVerifyCode(String key,long expired){
		removeExpired();
		String value = Common.getRandomCode(verifyCodeLength);
		put(SUBFIX_TELEPHONE,key,value,expired);
		return value;
	}
	public static String regEmailVerifyCode(String key,long expired){
		removeExpired();
		String value = Common.getRandomCode(verifyCodeLength);
		put(SUBFIX_EMAIL,key,value,expired);
		return value;
	}
	public static String regCode(String key,String value,long expired){
		removeExpired();
		put(SUBFIX_NORMAL,key,value,expired);
		return value;
	}
	/**
	 * 
	 * @param subFix 
	 * @param key
	 * @param value
	 * @param expired  为秒数
	 */
	private static void put(String subFix,String key,String value,long expired){
		long expired1 = System.currentTimeMillis()+expired*1000;
		key = subFix + key;
		if(expired1>System.currentTimeMillis()){
			if(RedisService.enabled){
				RedisService.set(RedisServerKeys.VERIFY_CODE+key, value+"::"+expired1, (int)expired);
			}else{
				vc.put(key, value+"::"+expired1);
			}
		}
	}
	private static String getVerifyCode(String subFix,String key){
		key = subFix + key;
		String v = RedisService.enabled?RedisService.get(RedisServerKeys.VERIFY_CODE+key):vc.get(key);
		if(v!=null){
			v = v.replaceAll("::\\d+?$","");
		}
		return v;
	}
	private static long getExpired(String key){
		String v = RedisService.enabled?RedisService.get(RedisServerKeys.VERIFY_CODE+key):vc.get(key);
		if(v!=null){
			try{
				String[] vs = v.split("::");
				return Long.parseLong(vs[vs.length-1], 10);
			}catch(Exception e){
				logger.error("获取超时时间出错:"+key+"->"+v+"["+RedisService.enabled+"]");
//				e.printStackTrace();
				return System.currentTimeMillis()+100000;
			}
		}
		return 0;
	}
	private static void remove(String subFix,String key){
		if(RedisService.enabled){
			RedisService.delKey(RedisServerKeys.VERIFY_CODE+subFix+key);
		}else{
			vc.remove(subFix+key);
		}
	}
	public static void removeTelephoneVerifyCode(String key){
		remove(SUBFIX_TELEPHONE,key);
	}
	public static void removeEmailVerifyCode(String key){
		remove(SUBFIX_EMAIL,key);
	}
	public static void removeCode(String key){
		remove(SUBFIX_NORMAL,key);
	}
	public static void removeExpired(){
		//由redis自身的定期机制去清理
		if(RedisService.enabled)return;
		Object[] ks =  vc.keySet().toArray();
		for(Object ko : ks){
			String k = String.valueOf(ko);
			if(System.currentTimeMillis()>=getExpired(k)) vc.remove(k);
		}
	}
}