package com.yz.common.cache.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * RedisTemplate操作redis数据库
 * @author yangzhao
 * @Description
 * @Date create by 22:26 18/3/3
 */
@Component
public class RedisTemplateImpl extends RedisCache {

    @Resource
    private RedisTemplate redisTemplate;

    public RedisTemplateImpl(RedisTemplate redisTemplate){
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void set(String key, String value) {
        redisTemplate.opsForValue().set(key,value);
    }

    @Override
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key,value);
    }

    @Override
    public String get(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }

    @Override
    public <T> T get(String key, Class<T> tClass) {
        Object o = redisTemplate.opsForValue().get(key);
        return (T) o;
    }

    @Override
    public void hashSet(String mname, String key, Object value) {
        redisTemplate.opsForHash().put(mname,key,value);
    }

    @Override
    public void hashSet(String mname, Map<String, Object> map) {
        redisTemplate.opsForHash().putAll(mname,map);
    }

    @Override
    public <T> T hashGet(String mname, String key, Class<T> clazz) {
        Object o = redisTemplate.opsForHash().get(mname, key);
        return (T) o;
    }

    @Override
    public Map hashGet(String mname) {
        Map entries = redisTemplate.opsForHash().entries(mname);
        return entries;
    }

    @Override
    public void hashRemove(String mname, String key) {
        redisTemplate.opsForHash().delete(mname,key);
    }

    @Override
    public void hashRemove(String mname) {
        redisTemplate.delete(mname);
    }

    @Override
    public void listAdd(String mname, String... value) {
        redisTemplate.opsForList().rightPushAll(mname,value);
    }

    @Override
    public <T> void listAdd(String mname, List<T> list) {
        redisTemplate.opsForList().rightPushAll(mname,list);
    }

    @Override
    public void listAdd(String mname, Object obj) {
        redisTemplate.opsForList().rightPush(mname,obj);
    }

    @Override
    public <T> List<T> listGet(String mname, Class<T> clazz) {
        List range = redisTemplate.opsForList().range(mname, 0, -1);
        return range;
    }

    @Override
    public <T> void listRemove(String mname, T value) {
        redisTemplate.opsForList().remove(mname,0,value);
    }

    @Override
    public void listRemove(String mname) {
        redisTemplate.delete(mname);
    }
}
