package com.yz.common.cache.redis;

import com.alibaba.fastjson.JSON;
import com.yz.common.core.redis.RedisUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * Jedis操作redis数据库
 * @author yangzhao
 * @date 2015/10/13
 */
public class JedisImpl extends RedisCache {

	private RedisUtil redisUtil;

	public JedisImpl(RedisUtil redisUtil) {
		this.redisUtil = redisUtil;
	}

	@Override
	public void set(String key, String value) {
		redisUtil.set(key,value);
	}

	@Override
	public void set(String key, Object object) {
		redisUtil.set(key, JSON.toJSONString(object));
	}

	@Override
	public String get(String key) {
		String s = redisUtil.get(key);
		return s;
	}

	@Override
	public <T> T get(String key, Class<T> tClass) {
		String s = redisUtil.get(key);
		T result = JSON.parseObject(s, tClass);
		return result;
	}

	@Override
	public void hashSet(String mname, String key, Object value) {
		redisUtil.hset(mname, key, JSON.toJSONString(value));
	}

	@Override
	public void hashSet(String mname, Map<String, Object> map) {
		Map<String,String> data=new HashMap<>();
		Set<String> keySet = map.keySet();
		Iterator<String> iterator = keySet.iterator();
		while(iterator.hasNext()){
			String key=iterator.next();
			Object obj=map.get(key);
			data.put(key, JSON.toJSONString(obj));
		}
		redisUtil.hmset(mname, data);
	}

	@Override
	public <T>T hashGet(String mname, String key,Class<T> clazz) {
		String data = redisUtil.hget(mname, key);
		if(StringUtils.isEmpty(data)){
			return null;
		}
		T parse = JSON.parseObject(data, clazz);
		return parse;
	}

	@Override
	public Map hashGet(String mname) {
		return redisUtil.hget(mname);
	}

	@Override
	public void hashRemove(String mname, String key) {
		redisUtil.remove(mname,key);
	}

	@Override
	public void hashRemove(String mname) {
		redisUtil.remove(mname);
	}

	@Override
	public void listAdd(String mname, String...values) {
		redisUtil.rpush(mname,values);
	}

	@Override
	public <T>void listAdd(String mname, List<T> list) {
		List<String> collect = list.stream().map(t -> {
			return JSON.toJSONString(t);
		}).collect(Collectors.toList());
		String[] strings = collect.toArray(new String[collect.size()]);
		redisUtil.rpush(mname,strings);
	}

	@Override
	public <T>List<T> listGet(String mname,Class<T> clazz) {
		List<String> list = redisUtil.getList(mname);
		if(CollectionUtils.isEmpty(list)){
			return null;
		}
		List<T> objects = list.stream().map(t -> JSON.parseObject(t,clazz)).collect(Collectors.toList());
		return objects;
	}

	@Override
	public <T> void listRemove(String mname, T value) {
		String jsonString = JSON.toJSONString(value);
		redisUtil.delList(mname, 0,jsonString);
	}

	@Override
	public void listRemove(String mname) {
		redisUtil.remove(mname);
	}

	@Override
	public void listAdd(String mname,Object t) {
		if(t!=null){
			redisUtil.rpush(mname,JSON.toJSONString(t));
		}
	}

}
