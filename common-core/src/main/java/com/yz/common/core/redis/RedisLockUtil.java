package com.yz.common.core.redis;

import org.apache.commons.lang.StringUtils;
import redis.clients.jedis.Jedis;

public class RedisLockUtil {

	private static int expired = 500;

	private  RedisUtil redisUtil;

	public boolean acquireLock(String lock, int count) {
		// 1. 通过SETNX试图获取一个lock
		boolean success = false;
		Jedis jedis = redisUtil.getJedis();
		long value = System.currentTimeMillis()+expired;
		long acquired = jedis.setnx(lock, String.valueOf(value));
		//System.out.println("wait lock---" + value);
		//SETNX成功，则成功获取一个锁
		if (acquired == 1) {
			success = true;
			//SETNX失败，说明锁仍然被其他对象保持，检查其是否已经超时
		} else {
			String obj = jedis.get(lock);
			if (!StringUtils.isEmpty(obj)) {
				long oldValue = Long.valueOf(obj);
				//超时
				if (oldValue < System.currentTimeMillis()) {
					//自动将key对应到value并且返回原来key对应的value。如果key存在但是对应的value不是字符串，就返回错误
					String getValue = jedis.getSet(lock, String.valueOf(value));
					// 获取锁成功
					if (Long.valueOf(getValue) == oldValue) {
						success = true;
					} else {
						System.out.println("已被其他线程捷足先登了");
						// 已被其他进程捷足先登了
						success = false;
					}
				} else {
					//未超时，则直接返回失败
					success = false;
				}
			}
		}
		RedisUtil.returnResource(jedis);

		if (!success) {
			if (count >= 1) {
				return false;
			}
			count++;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return acquireLock(lock, count);
		}
		return success;
	}

	//释放锁
	public void releaseLock(String lock) {
		Jedis jedis = redisUtil.getJedis();
		long current = System.currentTimeMillis();
		// 避免删除非自己获取得到的锁
		String obj = jedis.get(lock);
		if (obj != null) {
			if (current < Long.valueOf(obj)) {
				jedis.del(lock);
			}
		}
		RedisUtil.returnResource(jedis);
	}
	/**
	 * 并发请求锁
	 * @param key
	 * @param time
	 * @return
	 */
	public boolean requestLock(String key,long time){
		boolean result=true;
		Jedis jedis = redisUtil.getJedis();
		Long setnx = jedis.setnx(key, String.valueOf(time));
		if(setnx!=1){
			String oldValue = jedis.getSet(key, String.valueOf(time));
			if((time-(Long.parseLong(oldValue)))<500){
				result=false;
			}
		}
		jedis.close();
		return result;
	}
	
}
