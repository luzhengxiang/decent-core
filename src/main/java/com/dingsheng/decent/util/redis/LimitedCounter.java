package com.dingsheng.decent.util.redis;

import com.dingsheng.decent.util.core.PropertyConfig;
import com.dingsheng.decent.util.core.StringUtil;

public class LimitedCounter {
	public static boolean checkRegSmsVcCount(String key){
		boolean result = false;
		key = RedisServerKeys.REG_SMS_LIMITED+key;
		int limited = StringUtil.getNumberDefault(
				PropertyConfig.getPropertyValue("sms.limited.regCount"), "0");
		//限制开关为大于0时，且开启了redis
		if(limited>0 && RedisService.enabled){
			long nc = RedisService.incr(key);
			if(nc<=1)RedisService.expire(key, 24*60*60);
			if(nc>limited) result = true;
		}
		
		return result;
	}
}
