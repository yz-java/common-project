package com.yz.common.cache;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 基于JVM本地方法区缓存
 * ★★★★★ 如果需要缓存大量数据推荐使用redis缓存或适当调整JVM本地方法区
 */
public class HashListCache implements ICache {

	private static final Map<String, Map<String, Object>> bigMapMap = new ConcurrentHashMap<>();

	private static final Map<String, Map<String, Object>> bigObjectMap = new ConcurrentHashMap<>();

	@SuppressWarnings("rawtypes")
	private static final Map<String, CopyOnWriteArrayList> bigListMap = new ConcurrentHashMap<String,CopyOnWriteArrayList>();

	@Override
	public void set(String key, String value) {

	}

	@Override
	public void set(String key, Object object) {

	}

	@Override
	public String get(String key) {
		return null;
	}

	@Override
	public <T> T get(String key, Class<T> tClass) {
		return null;
	}

	@Override
	public void hashSet(String mname, String key, Object obj) {
		Map<String, Object> map = this.hashGet(mname);
		map.put(key, obj);
	}

	@Override
	public void hashSet(String mname, Map<String, Object> map) {
		Map<String, Object> mapTmp = this.hashGet(mname);
		mapTmp.putAll(map);
	}

	@SuppressWarnings("unchecked")
	@Override 
	public <T>T hashGet(String mname, String key,Class<T> clazz) {
		Map<String, Object> hashGet = this.hashGet(mname);
		if (hashGet == null) {
			return null;
		}
		return (T) hashGet.get(key);
	}

	@Override
	public String hashGet(String mname, String key) {
		return null;
	}

	@Override
	public Map<String, Object> hashGet(String mname) {
		Map<String, Object> map = bigMapMap.get(mname);
		if (map == null) {
			map = new ConcurrentHashMap<>();
			bigMapMap.put(mname, map);
		}
		return map;
	}

	@Override
	public void hashRemove(String mname, String key) {
		Map<String, Object> map = bigMapMap.get(mname);
		if (map == null) {
			return;
		}
		map.remove(key);
	}

	@Override
	public void hashRemove(String mname) {
		bigMapMap.remove(mname);
	}

	@Override
	public void listAdd(String mname, String... value) {
		this.listGet(mname, null).add(0, value);
	}

	@Override
	public <T> void listAdd(String mname, List<T> list) {
		List<Object> listGet = this.listGet(mname, null);
		listGet.addAll(list);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> listGet(String mname, Class<T> clazz) {
		List<T> list = bigListMap.get(mname);
		if (list == null) {
			CopyOnWriteArrayList copyOnWriteArrayList = new CopyOnWriteArrayList<>();
			bigListMap.put(mname,copyOnWriteArrayList);
		}
		return list;
	}


	@SuppressWarnings("unchecked")
	@Override
	public <T> void listRemove(String mname, T value) {
		List<T> listGet = bigListMap.get(mname);
		while (listGet.contains(value)) {
			listGet.remove(value);
		}
	}

	@Override
	public void listRemove(String mname) {
		bigListMap.remove(mname);
	}

	@Override
	public void listAdd(String mname, Object obj) {
		if(obj!=null){
			List<Object> listGet = this.listGet(mname, null);
			listGet.add(obj);
		}
	}

}
