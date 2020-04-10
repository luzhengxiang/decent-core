package com.dingsheng.decent.util.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

public class PropertyConfig {

	private static Logger logger = LoggerFactory
			.getLogger(PropertyConfig.class);

	private static HashMap<String, PropertyConfig> propertiesMap = new HashMap<String, PropertyConfig>();

	/**
	 * 读取配置文件路径前缀
	 */
	private static final String finaName_prefixion_path = "/";

	/**
	 * 读取配置文件路径后缀
	 */
	private static final String finaName_suffix_path = ".properties";

	/**
	 * 默认的配置文件名
	 */
	private static final String defaultFname = "default";

	private Properties properties;

	private void setProperties(Properties properties) {
		this.properties = properties;
	}

	private PropertyConfig() {
	}

	/**
	 * 从默认property文件中取值
	 * 
	 * @param key
	 * @return
	 */
	public static String getPropertyValue(String key) {
		if(StringUtil.isEmpty(key))return null;
		String[] ks = key.split("\\.");
		String v = getPropertyConfig(PropertyConfig.defaultFname).getValue(key);
		return StringUtil.isEmpty(v)?getPropertyConfig(ks[0]).getValue(key):v;
//		return getPropertyConfig(PropertyConfig.defaultFname).getValue(key);
	}

	/**
	 * property文件必须存在项目resources目录下 例如,a.property 只需要传入a
	 * 
	 * @param Fname
	 * @return
	 */
	public static PropertyConfig getPropertyConfig(String Fname) {
		if (propertiesMap.containsKey(Fname)) {
			return propertiesMap.get(Fname);
		} else {
			PropertyConfig pc = new PropertyConfig();
			return pc.loadPropertyConfig(Fname);
		}
	}

	/**
	 * 
	 * @param fileName
	 * @return
	 */
	private PropertyConfig loadPropertyConfig(String fileName) {

		PropertyConfig pc = new PropertyConfig();
		Properties pp = new Properties();
		pc.setProperties(pp);
		propertiesMap.put(fileName, pc);
		InputStream in = null;
		try {
			in = PropertyConfig.class.getResourceAsStream(pc
					.getPropertyPath(fileName));
			pp.load(in);
		} catch (Exception e) {
			logger.warn(String.format("properties文件加载失败：未找到【%s.properties】文件",fileName));
		} finally {
			if (null != in)
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return pc;
	}

	/**
	 * 
	 * @param fileName
	 * @return
	 */
	private String getPropertyPath(String fileName) {
		StringBuffer sb = new StringBuffer();
		sb.append(finaName_prefixion_path).append(fileName)
				.append(finaName_suffix_path);
		return sb.toString();
	}

	/**
	 * 
	 * @param key
	 * @return
	 */
	public String getValue(String key) {
		return properties.getProperty(key);
	}

	/**
	 * 
	 * @param key
	 * @return
	 */
	public Integer getIntValue(String key) {
		String value = properties.getProperty(key);
		if (StringUtil.isEmpty(value)) {
			return null;
		}
		return Integer.parseInt(value);
	}

	/**
	 * 
	 * @param key
	 * @return
	 */
	public Long getLongValue(String key) {
		String value = properties.getProperty(key);
		if (StringUtil.isEmpty(value)) {
			return null;
		}
		return Long.parseLong(value);
	}
	public boolean getBooleanValue(String key){
		String value = properties.getProperty(key);
		return StringUtil.isTrue(value);
	}

}