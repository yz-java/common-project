package com.yz.common.distributed.lock;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author: yangzhao
 * @Date: 2019/4/29 18:22
 * @Description:
 */
public class DistributedLock {

    @Resource(name = "redisTemplate")
    private RedisTemplate redisTemplate;

    public DistributedLock() {
    }

    public DistributedLock(RedisTemplate redisTemplate) {
        this.redisTemplate=redisTemplate;
    }

    /**
     *
     * @param key 锁标识
     * @param attempt 重试次数
     * @return
     */
    public boolean lock(String key,int attempt){
        Boolean absent = redisTemplate.opsForValue().setIfAbsent(key, System.currentTimeMillis());
        if (absent){
            return true;
        }
        if (attempt>0){
            attempt--;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return lock(key,attempt);
        }
        return false;
    }

    /**
     *
     * @param key 锁标识
     * @param time 过期时间
     * @param timeUnit 过期时间单位
     * @param attemptNum 重试次数
     * @return
     */
    public boolean lock(String key, long time, TimeUnit timeUnit,int attemptNum){
        boolean lock = this.lock(key, time, timeUnit);
        attemptNum--;
        if (!lock){
            if (attemptNum<0){
                return false;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return lock(key, time, timeUnit, attemptNum);
        }
        return true;
    }

    public boolean lock(String key, long time, TimeUnit timeUnit){
        List<Boolean> results = redisTemplate.executePipelined(new RedisCallback<Boolean>() {

            RedisSerializer keySerializer = redisTemplate.getKeySerializer();

            RedisSerializer valueSerializer = redisTemplate.getValueSerializer();

            @Override
            public Boolean doInRedis(RedisConnection redisConnection) throws DataAccessException {
                redisConnection.openPipeline();
                byte[] keySerialize = keySerializer.serialize(key);
                byte[] valueSerialize = valueSerializer.serialize(System.currentTimeMillis());
                redisConnection.setNX(keySerialize, valueSerialize);
                long l = timeUnit.toSeconds(time);
                redisConnection.expire(keySerialize, l);
                return null;
            }
        });
        for (Boolean result :results) {
            if (!result){
                return false;
            }
        }
        return true;
    }

    public void unlock(String key){
        redisTemplate.delete(key);
    }
}
