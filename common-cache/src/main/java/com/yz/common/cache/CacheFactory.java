package com.yz.common.cache;


import com.yz.common.cache.jvm.JvmCacheImpl;
import com.yz.common.cache.redis.RedisTemplateImpl;

/**
 * 缓存工厂类
 * @author yangzhao 2015年10月16日
 *
 */
public class CacheFactory {

	private ICache jedisImpl;

	private JvmCacheImpl JvmCacheImpl;

	private RedisTemplateImpl redisTemplateImpl;

	public CacheFactory(ICache jedisImpl, JvmCacheImpl JvmCacheImpl, RedisTemplateImpl redisTemplateImpl) {
		this.jedisImpl = jedisImpl;
		this.JvmCacheImpl = JvmCacheImpl;
		this.redisTemplateImpl = redisTemplateImpl;
	}


	public ICache getJedisImpl() {
		if (jedisImpl==null){
			throw new RuntimeException("jedisImpl IS NULL");
		}
		return jedisImpl;
	}

	public ICache getjvmCacheImpl() {
		if (JvmCacheImpl ==null){
			throw new RuntimeException("JvmCacheImpl IS NULL");
		}
		return JvmCacheImpl;
	}


	public ICache getRedisTemplateImpl() {
		if (redisTemplateImpl==null){
			throw new RuntimeException("redisTemplateImpl IS NULL");
		}
		return redisTemplateImpl;
	}

}
