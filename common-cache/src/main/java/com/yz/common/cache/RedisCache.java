package com.yz.common.cache;

import com.yz.common.core.json.JacksonUtil;
import com.yz.common.core.json.JSON;
import com.yz.common.core.redis.RedisUtil;
import org.apache.commons.lang.StringUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * Redis 缓存
 * Created by yangzhao on 2015/10/13.
 * 添加Stream并行聚合操作 update by yangzhao on 2016/10/14
 */
public class RedisCache implements ICache {
	@Override
	public void set(String key, String value) {
		RedisUtil.getInstance().set(key,value);
	}

	@Override
	public void set(String key, Object object) {
		RedisUtil.getInstance().set(key, JacksonUtil.parse(object));
	}

	@Override
	public String get(String key) {
		String s = RedisUtil.getInstance().get(key);
		return s;
	}

	@Override
	public <T> T get(String key, Class<T> tClass) {
		String s = RedisUtil.getInstance().get(key);
		T result = JacksonUtil.parse(s, tClass);
		return result;
	}

	@Override
	public void hashSet(String mname, String key, Object value) {
		RedisUtil.getInstance().hset(mname, key, JacksonUtil.parse(value));
	}

	@Override
	public void hashSet(String mname, Map<String, Object> map) {
		Map<String,String> data=new HashMap<>();
		Set<String> keySet = map.keySet();
		Iterator<String> iterator = keySet.iterator();
		while(iterator.hasNext()){
			String key=iterator.next();
			Object obj=map.get(key);
			data.put(key, JacksonUtil.parse(obj));
		}
		RedisUtil.getInstance().hmset(mname, data);
	}

	@Override
	public <T>T hashGet(String mname, String key,Class<T> clazz) {
		RedisUtil redisUtil = RedisUtil.getInstance();
		boolean exist = redisUtil.exist(mname);
		if (!exist){
			return null;
		}
		String data = redisUtil.hget(mname, key);
		if(StringUtils.isEmpty(data)){
			return null;
		}
		T parse = JacksonUtil.parse(data, clazz);
		return parse;
	}

	@Override
	public String hashGet(String mname, String key) {
		String data = RedisUtil.getInstance().hget(mname, key);
		return data;
	}

	@Override
	public Map<String, Object> hashGet(String mname) {
		Map<String, String> hget = RedisUtil.getInstance().hget(mname);
		if(hget.isEmpty()){
			return null;
		}
		Set<String> keySet = hget.keySet();
		Iterator<String> iterator = keySet.iterator();
		Map<String,Object> map=new HashMap<>();
		while(iterator.hasNext()){
			String key=iterator.next();
			Object obj= JacksonUtil.parse(hget.get(key), Object.class);
			map.put(key, obj);
		}
		return map;
	}

	@Override
	public void hashRemove(String mname, String key) {
		RedisUtil.getInstance().remove(mname,key);
	}

	@Override
	public void hashRemove(String mname) {
		RedisUtil.getInstance().remove(mname);
	}

	@Override
	public void listAdd(String mname, String...values) {
		RedisUtil.getInstance().rpush(mname,values);
	}

	@Override
	public <T>void listAdd(String mname, List<T> list) {
		ArrayList<String> resultList = list.stream().map(t -> JacksonUtil.parse(t)).
				collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
		String[] datas = resultList.toArray(new String[resultList.size()]);
		RedisUtil.getInstance().rpush(mname,datas);
	}

	@Override
	public <T>List<T> listGet(String mname,Class<T> clazz) {
		List<String> list = RedisUtil.getInstance().getList(mname);
		if(list.isEmpty()){
			return null;
		}
		List<T> objects = list.stream().map(t -> JacksonUtil.parse(t,clazz)).collect(Collectors.toList());
		return objects;
	}

	@Override
	public <T> void listRemove(String mname, T value) {
		String jsonString = JSON.getJsonInterface().toJsonString(value);
		RedisUtil.getInstance().delList(mname, 0,jsonString);
	}

	@Override
	public void listRemove(String mname) {
		RedisUtil.getInstance().remove(mname);
	}

	@Override
	public void listAdd(String mname,Object t) {
		String jsonString = JSON.getJsonInterface().toJsonString(t);
		if(t!=null){
			RedisUtil.getInstance().rpush(mname,jsonString);
		}
	}

}
