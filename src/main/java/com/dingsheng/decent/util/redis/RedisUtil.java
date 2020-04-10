package com.dingsheng.decent.util.redis;


import com.dingsheng.decent.util.core.PropertyConfig;
import com.dingsheng.decent.util.core.StringUtil;
import com.dingsheng.decent.util.encrypt.Blowfish;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashMap;


/**
 * 
 * @description:Redis 连接池
 *
 * @author     :laoas
 * @create     :2012-6-15
 *
 * 
 */
public class RedisUtil {
	private final static Logger logger = LoggerFactory.getLogger(RedisUtil.class);
	private static int DBINDEX;
	private static String  HOST;//IP的地址
	private static int TIMEOUT = 2*1000;//超时时间
	private static HashMap<Integer, String> POOLMAP = new HashMap();
	private static HashMap<Integer, String> POOLMAP_BAK = new HashMap();
	private static JedisPool pool;
	static{
		
		JedisPoolConfig config = new JedisPoolConfig();
//        conf.setMaxActive(50);
		config.setMaxTotal(50);
        config.setMaxIdle(5);  
        config.setMinIdle(0);  
//        conf.setMaxWait(15000);
        config.setMaxWaitMillis(15000);
        config.setMinEvictableIdleTimeMillis(300000);  
        config.setSoftMinEvictableIdleTimeMillis(-1);  
        config.setNumTestsPerEvictionRun(3);  
        config.setTestOnBorrow(true);//获取前加以测试  
        config.setTestOnReturn(false);  
        config.setTestWhileIdle(false);  
        config.setTimeBetweenEvictionRunsMillis(600000);//
//        conf.setWhenExhaustedAction((byte)1);
        config.setBlockWhenExhausted(true);
        try{//"192.168.1.101", 6379)
        	if(StringUtil.isTrue(PropertyConfig.getPropertyValue("redis.pwdEnabled"))){
        		pool = new JedisPool(config,
    				PropertyConfig.getPropertyValue("redis.Host") , 
    				Integer.parseInt(PropertyConfig.getPropertyValue("redis.Port")),
					TIMEOUT, Blowfish.decode(PropertyConfig.getPropertyValue("redis.pwd"))
				);
        	}else{
        		pool = new JedisPool(config,
    				PropertyConfig.getPropertyValue("redis.Host") , 
    				Integer.parseInt(PropertyConfig.getPropertyValue("redis.Port"))
				);
        	}
        }catch(Exception e){
        	e.printStackTrace();
        }

	}

	/**
	 * 
	 * @description:获取JedisPool(读取操作)
	 * @create     :2012-6-15
	 * @author     :laoas
	 * @param uid 用户id
	 * @return
	 * @return     :JedisPool
	 */
	protected static Jedis getPool(long uid){
		
		try {
			return pool.getResource();
		} catch (Exception e) {
			logger.error("获取redis连接失败:"+e.getMessage());
			try {
				return getPoolBak(uid);
			} catch (Exception e2) {
				logger.error("2");
			}
		}
		return null;
	}

	/**
	 * 
	 * @description:读取备用连接池
	 * @create     :2012-6-15
	 * @author     :laoas
	 * @param uid 用户id
	 * @return
	 * @return     :JedisPool
	 */
	protected static Jedis getPoolBak(long uid){
		//int index = (int) (uid % DBINDEX);
		return  pool.getResource();
	}
	/**
	 * 
	 * @description:回收Jedis
	 * @create     :2012-6-15
	 * @author     :laoas
	 * @param jedis
	 * @return     :void
	 */
	protected static void returnResource(Jedis jedis){
		getPool().returnResource(jedis);
	}
	
	

	public static  void closeRedis(Jedis jedis){
		if(jedis!=null)returnResource(jedis);
	}
	
	public static void breakResource(Jedis jedis) {
		if(jedis!=null)getPool().returnBrokenResource(jedis);
	}
	
	
	
	
	public static JedisPool getPool() {
		return pool;
	}

	public static void setPool(JedisPool pool) {
		RedisUtil.pool = pool;
	}


	public static void main(String[] args) {
		System.out.println(Blowfish.encode("3n6syenM"));
	}
}
