package com.dingsheng.decent.util.encrypt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class GwUtils {
	private static Logger logger = LoggerFactory.getLogger(GwUtils.class);
	
	public final static String privateKey = "merCode";
	public final static String accountKey = "account";
	public final static String signKey = "sign";
	public static boolean checkAuthorize(HttpServletRequest request, String key) {
		TreeMap<String, String>p = getSortedParameter(request);
		String myencode = encode(p, key);
		boolean t = false;
		logger.info(String.format("检查数据签名：%s <===> %s (%s)", myencode,
				p.get(signKey), t = myencode.equalsIgnoreCase(p.get(signKey))));
		return t;// myencode.equalsIgnoreCase(p.get(signKey));
	}
	public static String encode(SortedMap<String, String> m, String key){
		String equalsFlag = "=";
		StringBuffer sb = new StringBuffer();
		for ( Map.Entry< String, String> entry : m.entrySet() ) {
			if(!signKey.equals(entry.getKey())){
				sb.append(entry.getKey()).append(equalsFlag).append(entry.getValue()).append("&");
			}
		}

		sb.append(privateKey).append(equalsFlag).append(key);
		return MD5.encode(sb.toString());
	}
	public static TreeMap<String, String> getSortedParameter(
			HttpServletRequest request) {
		TreeMap<String, String> t = new TreeMap<String, String>();
		Enumeration<?> enumration = request.getParameterNames();
		while (enumration.hasMoreElements()) {
			String parameter = enumration.nextElement().toString();
			t.put(parameter, request.getParameter(parameter));
		}
		return t;
	}
}
