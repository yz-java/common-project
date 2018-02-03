package com.yz.common.cache;


import com.yz.common.core.enums.CacheTypeEnum;

/**
 * 缓存工厂类
 * @author yangzhao 2015年10月16日
 *
 */
public class CacheFactory {

	private ICache cache;

	private static CacheFactory instance = new CacheFactory();

	public static CacheFactory getInstance() {
		return instance;
	}

	public synchronized void init(int type) {
		if (type == CacheTypeEnum.JVM.getType()) {
			cache = new HashListCache();
		} else if (type == CacheTypeEnum.REDIS.getType()) {
			cache = new RedisCache();
		}
	}

	public ICache getDefault() {
		if (cache==null){
			throw new RuntimeException("请预先加载该工厂");
		}
		return cache;
	}

}
