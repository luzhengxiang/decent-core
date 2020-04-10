package com.dingsheng.decent.util.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

import java.util.ArrayList;
import java.util.List;

public class PipeRedisService extends RedisUtil{
	private Jedis jds=null;
	private Pipeline pl=null;
	private List rps = new ArrayList();
	
	public void start(){
		jds = getPool(1);
		pl = jds.pipelined();
	}
	public void incr(String key){
		Response<Long> rp = pl.incr(key);
		rps.add(rp);
	}
	public void set(String key, String value){
		
	}
	public void close(){
		super.returnResource(jds);
	}
	public List<?> commit(){
		pl.sync();
		return rps;
	}
}
