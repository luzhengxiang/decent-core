package com.dingsheng.decent.util.redis;

import com.dingsheng.decent.util.core.PropertyConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class RedisService extends RedisUtil {
	private final static Logger logger = LoggerFactory.getLogger(RedisUtil.class);
	public final static boolean enabled = "true".equalsIgnoreCase(PropertyConfig.getPropertyValue("redis.enabled"));

	public static Set<String> zrange(String key, int start, int end, Long uid) {
		Jedis jedis = null;
		Set<String> resultSet = null;
		try {
			jedis = getPool(uid);
			resultSet = jedis.zrange(key, start, end);
			return resultSet;
		} catch (Exception e) {
			logger.error("zrange:"+e.getMessage());
			breakResource(jedis);
			jedis = null;
		} finally {
			if (null != jedis) {
				closeRedis(jedis);
			}
		}
		return null;
	}

	/**
	 * 
	 * @description:
	 * @create :2012-6-19
	 * @author :laoas
	 * @param key
	 * @param max
	 * @param min
	 * @param uid
	 * @return
	 * @return :Set<String>
	 */
	public static Set<String> zrevrangeByScore(String key, double max, double min, Long uid) {
		Jedis jedis = null;
		Set<String> result = null;
		try {
			jedis = getPool(uid);
			if (null != jedis) {
				result = jedis.zrevrangeByScore(key, max, min);
			}
			return result;
		} catch (Exception e) {
			logger.error("zrevrangeByScore:"+e.getMessage());
			breakResource(jedis);
			jedis = null;
		} finally {
			if (null != jedis) {
				closeRedis(jedis);
			}

		}
		return null;
	}
	
	/**
	 * 返回 rank max to mix 的有序集合
	 * @param key
	 * @param min
	 * @param max
	 * @param uid
	 * @return
	 */
	public static Long zcount(String key, Double min, Double max, Long uid){
		Jedis jedis = null;
		Long result = null;
		try {
			jedis = getPool(uid);
			if (null != jedis) {
				if(min == 0D && max == -1D){
					result = jedis.zcount(key, "-inf", "+inf");
				}else{
					result = jedis.zcount(key, min, max);
				}
			}
			return result;
		} catch (Exception e) {
			logger.error("zcount:"+e.getMessage());
			breakResource(jedis);
			jedis = null;
		} finally {
			if (null != jedis) {
				closeRedis(jedis);
			}

		}
		return null;
	}
	

	
	
	/**
	 * 删除有序集合
	 * @param key
	 * @param start
	 * @param end
	 * @param uid
	 */
	
	public static void  zremRangeByRank(String key ,long start ,long end , long uid){
		
		Jedis jedis = null;
		try {
			jedis = getPool(uid);
			if (null != jedis) {
				jedis.zremrangeByRank(key, start, end);	
			}
		} catch (Exception e) {
			logger.error("zremRangeByRank:"+e.getMessage());
			breakResource(jedis);
			jedis = null;
		} finally {
			if (null != jedis) {
				closeRedis(jedis);
			}
		}

		 
	}
	
	
	
	
	

	/**
	 * key-value 设置
	 * @param key
	 * @param value
	 * @throws Exception
	 */
	protected static void set( String key, String value){
		Jedis jedis = null;
		try {
			jedis = getPool(1);
			if (null != jedis) {
				jedis.set(key, value);
			}
		} catch (Exception e) {
			logger.error("set(key,value):"+e.getMessage());
			breakResource(jedis);
			jedis = null;
		} finally {
			if (null != jedis) {
				closeRedis(jedis);
			}
		}
	}

	
	/**
	 * 
	 * @param key
	 * @param value
	 * @param time 过期时间，单位为秒
	 */
	public static void set(String key,String value,int time){

		Jedis jedis = null;
		try {
			jedis = getPool(1);
			if (null != jedis) {
				  jedis.set(key, value);
				  jedis.expire(key, time);
			}
		} catch (Exception e) {
			logger.error(key+","+value+","+time);
			logger.error("set(key,value,time):"+e.getMessage());
			breakResource(jedis);
			jedis = null;
		} finally {
			if (null != jedis) {
				closeRedis(jedis);
			}
		}
	
	}
	/**
	 * 
	 * @param key
	 * @param value
	 */
	public static void set(String key,String value,long expire){

		Jedis jedis = null;
		try {
			jedis = getPool(1);
			if (null != jedis) {
				  jedis.set(key, value);
				  jedis.expireAt(key, expire);
			}
		} catch (Exception e) {
			logger.error("set(key,value,expire):"+e.getMessage());
			breakResource(jedis);
			jedis = null;
		} finally {
			if (null != jedis) {
				closeRedis(jedis);
			}
		}
	
	}
	
	
	/**
	 * 
	 * @param key
	 * @param value
	 * @param time 制定日期数据过期
	 */
	public static void set(String key,String value,Date time){

		Jedis jedis = null;
		try {
			jedis = getPool(1);
			if (null != jedis) {
				  long tempTime = time.getTime()/1000;
				  Date date = new Date();
				  int temp = (int)(tempTime-date.getTime()/1000);
				  jedis.set(key, value);
				  jedis.expire(key, temp);
				
			}
		} catch (Exception e) {
			logger.error("set(key,value,date):"+e.getMessage());
			breakResource(jedis);
			jedis = null;
		} finally {
			if (null != jedis) {
				closeRedis(jedis);
			}
		}
	
	}
	/**
	 * set 
	 * @param key
	 * @param value
	 * @param expire
	 * @return
	 */
	public static byte[] set(byte[] key,byte[] value,int expire){
		Jedis jedis = getPool(1);
		try{
			jedis.set(key,value);
			if(expire != 0){
				jedis.expire(key, expire);
		 	}
		} catch (Exception e) {
			logger.error("set(byte[] key,byte[] value,int expire):"+e.getMessage());
			breakResource(jedis);
			jedis = null;
		}finally{
			closeRedis(jedis);
		}
		return value;
	}
	public static byte[] set(byte[] key,byte[] value){
		Jedis jedis = getPool(1);
		try{
			jedis.set(key,value);
//			if(this.expire != 0){
//				jedis.expire(key, this.expire);
//		 	}
		} catch (Exception e) {
			logger.error("set(byte[] key,byte[] value):"+e.getMessage());
			breakResource(jedis);
			jedis = null;
		}finally{
			closeRedis(jedis);
		}
		return value;
	}
	
	
	/**
	 * key-value 设置
	 * @param key
	 * @throws Exception
	 */
	public  static String get( String key){
		Jedis jedis = null;
		try {
			jedis = getPool(1);
			if (null != jedis) {
				Object v = jedis.get(key);
				return v==null?null:String.valueOf(v);
			}else 
				return null;	
		} catch (Exception e) {
			logger.error("get(key):"+e.getMessage());
			breakResource(jedis);
			jedis = null;
			return null;
		} finally {
			if (null != jedis) {
				closeRedis(jedis);
			}
		}
	}
	/**
	 * 
	 * @param key
	 * @return
	 */
	public static byte[] get(byte[] key){
		byte[] value = null;
		Jedis jedis = getPool(1);
		try{
			value = jedis.get(key);
		} catch (Exception e) {
			logger.error("byte[] get(byte[] key):"+e.getMessage());
			breakResource(jedis);
			jedis = null;
			return null;
		}finally{
			closeRedis(jedis);
		}
		return value;
	}
	
	
	/**
	 * 批量获取字符串值 
	 * @throws Exception
	 */
	public  static List<String> mGet( String keys[])throws Exception{
		Jedis jedis = null;
		try {
			jedis = getPool(1);
			if (null != jedis) {
				return jedis.mget(keys);
			}else 
				return null;	
		} catch (Exception e) {
			logger.error("mGet(keys[]):"+e.getMessage());
			breakResource(jedis);
			jedis = null;
			return null;
		} finally {
			if (null != jedis) {
				closeRedis(jedis);
			}
		}
	}

	
	/**
	 * 无序集合添加
	 * @param jedis
	 * @param uid
	 * @param key
	 * @param values
	 * @return
	 * @throws Exception
	 */
	protected static Long sadd(Jedis jedis, Long uid, String key, String... values) throws Exception {
		try {
			if (null != jedis) {
				return jedis.sadd(key, values);
			}
		}catch(Exception e){
			logger.error("sadd:"+e.getMessage());
			breakResource(jedis);
			jedis = null;
		}finally {
			if (null != jedis) {
				closeRedis(jedis);
			}
		}
		return -1L;
	}
	
	
	
	
	/**
	 * map集合赋值
	 * @param key    集合key
	 * @param member 成员key
	 * @param num    初始值
	 */
	public static void putMap(String key,String member,long num){
		Jedis jedis = null;
		jedis = getPool(1);
		if(jedis != null){
			jedis.hdel(key, member);
			jedis.hincrBy(key, member, num);
			closeRedis(jedis);
		}
	}
	
	
	/**
	 * 
	 * @param key
	 * @param member
	 * @param num
	 * @param times 过期
	 */
	public static void putMap(String key,String member,long num,int times){
		Jedis jedis = null;
		jedis = getPool(1);
		if(jedis != null){
			jedis.hdel(key, member);
			jedis.hincrBy(key, member, num);
			closeRedis(jedis);
		}
	}
	
	
	
	
	/**
	 * map键�?自加num
	 * @param num
	 * @param key
	 * @param member
	 */
	public static void hincrByNum(long num,String key,String member){		
		Jedis jedis = null;
		jedis = getPool(1);
		if(jedis != null){
			jedis.hincrBy(key, member, num);
			closeRedis(jedis);
		}
	}
	
	
	/**
	 * 获取map成员值
	 * @param key
	 * @param member
	 * @return
	 */
	public static long getHmapMemberValues(String key,String member){
		Jedis jedis = null;
		jedis = getPool(1);
		String tmpValue = "";
		if(jedis != null){
			tmpValue = jedis.hget(key, member);
		    closeRedis(jedis);
		}
		if(tmpValue != null && tmpValue.trim().length()>0){
			return Long.parseLong(tmpValue);
		}else{
			return 0;
		}
	}
	
	
	/**
	 * 获取map成员值
	 * @param key
	 * @param member
	 * @return
	 */
	public static String getHMemberValues(String key,String member){
		Jedis jedis = null;
		jedis = getPool(1);
		String tmpValue = "";

		if(jedis != null){
			tmpValue = jedis.hget(key, member);
		    closeRedis(jedis);
		}
		if(tmpValue != null && tmpValue.trim().length()>0){
			return tmpValue;
		}else{
			return null;
		}
	}
	
	/**
	 * 获取无序set全部值
	 * 
	 * @param key
	 * @return
	 */
	public static Set<String> getAllSet(String key) {
		Jedis jedis = null;
		jedis = getPool(1);
		Set<String> set = new HashSet<>();
		if (jedis != null) {
			set = jedis.smembers(key);
			closeRedis(jedis);
		}
		return set;
	}
	
	
	/**
	 * 删除无序set
	 * @param key
	 * @param members
	 */
	public static void removeMember(String key,String members) {
		Jedis jedis = null;
		try {
			jedis = getPool(1);
			jedis.srem(key, members);
		} catch (Exception e) {
			logger.error("removeMember:" + e.getMessage());
			breakResource(jedis);
			jedis = null;
		} finally {
			if (jedis != null) {
				closeRedis(jedis);
			}
		}
	}
	public static int expire(String key, int time){
		Jedis jedis = null;
		try{
			jedis = getPool(1);
			jedis.expire(key, time);
			return time;
		}catch(Exception e){
			logger.error("设置过期时间出错:"+e.getMessage());
			breakResource(jedis);
			jedis = null;
		}finally{
			closeRedis(jedis);
		}
		return -1;
	}
	/**
	 * 
	 * @param key
	 * @param member
	 * @return
	 */
	public static long delKey(String key){
		Jedis jedis = null;
		try{
			jedis = getPool(1);
			Long result = jedis.del(key);
			return result==null?0:result.longValue();
		} catch (Exception e) {
			logger.error("delKey:"+e.getMessage());
			breakResource(jedis);
			jedis = null;
		} finally {
			closeRedis(jedis);
		}
		return 0;
	}
	public static void del(byte[] key){
		Jedis jedis = getPool(1);
		try{
			jedis.del(key);
		}catch (Exception e) {
			logger.error("del(byte[] key):"+e.getMessage());
			breakResource(jedis);
			jedis = null;
		}finally{
			closeRedis(jedis);
		}
	}
	/**
	 * 对key进行 整型递增1
	 * @param key
	 * @param v
	 * @return
	 */
	public static long incr(String key){
		Jedis jedis = null;
		Long result = 0L;
		try{
			jedis = getPool(1);
			result = jedis.incr(key);
		}catch (Exception e) {
			logger.error("incr:"+e.getMessage());
			breakResource(jedis);
			jedis = null;
		} finally {
			closeRedis(jedis);
		}
		return result==null?0:result.longValue();
	}
	/**
	 * 对key进行 整型递增1
	 * @param key
	 * @param v
	 * @return
	 */
	public static long incr(String key,int time){
		Jedis jedis = null;
		Long result = 0L;
		try{
			jedis = getPool(1);
			result = jedis.incr(key);
			if(result==1){
				expire(key, time);
			}
		}catch (Exception e) {
			logger.error("incr(key,time):"+e.getMessage());
			breakResource(jedis);
			jedis = null;
		} finally {
			closeRedis(jedis);
		}
		return result==null?0:result.longValue();
	}
	/**
	 * 对key进行 整型递减1
	 * @param key
	 * @param v
	 * @return
	 */
	public static long decr(String key){
		Jedis jedis = null;
		Long result = 0L;
		try {
			jedis = getPool(1);
			result = jedis.decr(key);
		} catch (Exception e) {
			logger.error("decr:"+e.getMessage());
			breakResource(jedis);
			jedis = null;
		} finally {
			if (jedis != null) {
				closeRedis(jedis);
			}
		}
		return result==null?0:result.longValue();
	}
	/**
	 * 对key进行指定值增量
	 * @param key
	 * @param v
	 * @return
	 */
	public static BigDecimal incrBy(String key,BigDecimal v){
		Jedis jedis = null;
		BigDecimal result = BigDecimal.ZERO;
		try {
			jedis = getPool(1);
			result = new BigDecimal(jedis.incrByFloat(key, v.doubleValue()));
		} catch (Exception e) {
			logger.error("incrBy:" + e.getMessage());
			breakResource(jedis);
			jedis = null;
		} finally {
			if (jedis != null) {
				closeRedis(jedis);
			}
		}
		return result;
	}
	/**
	 * 判断key是否存在
	 * @param key
	 * @return
	 */
	public static boolean exists(String key){
		Jedis jedis = null;
		Boolean result = false;
		try {
			jedis = getPool(1);
			result = jedis.exists(key);
		} catch (Exception e) {
			logger.error("exists:" + e.getMessage());
			breakResource(jedis);
			jedis = null;
		} finally {
			if (jedis != null) {
				closeRedis(jedis);
			}
		}
		return result==null?false:result.booleanValue();
	}
	/**
	 * size
	 */
	public static Long dbSize(){
		Long dbSize = 0L;
		Jedis jedis = getPool(1);
		try{
			dbSize = jedis.dbSize();
		} catch (Exception e) {
			logger.error("dbSize:" + e.getMessage());
			breakResource(jedis);
			jedis = null;
		} finally{
			closeRedis(jedis);
		}
		return dbSize;
	}
	/**
	 * keys
	 * @param pattern
	 * @return
	 */
	public static Set<byte[]> keys(String pattern){
		Set<byte[]> keys = null;
		Jedis jedis = getPool(1);
		try{
			keys = jedis.keys(pattern.getBytes());
		}catch (Exception e) {
			logger.error("keys:" + e.getMessage());
			breakResource(jedis);
			jedis = null;
		} finally{
			closeRedis(jedis);
		}
		return keys;
	}

	public static Long publish(String channel, String message){
		Jedis jedis = null;
		try {
			jedis = getPool(1);
			return jedis.publish(channel, message);
		} catch (Exception e) {
			logger.error("publish:" + e.getMessage(), e);
			breakResource(jedis);
			jedis = null;
		} finally {
			closeRedis(jedis);
		}
		return 0L;
	}

	/**
	 * 订阅 消息
	 * @param channel 消息频道
	 * @param jps 消费者实现
	 */
	public static void subscribe(String channel, JedisPubSub jps){
		Jedis jedis = null;
		try {
			jedis = getPool(1);
			jedis.subscribe(jps, channel);
		} catch (Exception e) {
			logger.error("subscribe:" + e.getMessage(), e);
			breakResource(jedis);
			jedis = null;
		} finally {
			closeRedis(jedis);
		}
	}

	public static void stringSet(String key,String value){
		Jedis jedis = null;
		try {
			jedis = getPool(1);
			jedis.set(key,value);
		} catch (Exception e) {
			logger.error("set:" + e.getMessage(), e);
			breakResource(jedis);
			jedis = null;
		} finally {
			closeRedis(jedis);
		}
	}
}
