package com.yz.common.cache;

import java.util.List;
import java.util.Map;
import java.util.Set;
import com.alibaba.fastjson.JSON;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.*;

/**
 * Redis工具类
 *
 * @author yangzhao at 2016年3月27日
 */
@SuppressWarnings("unused")
public class RedisUtil {

    private static final Logger logger = LoggerFactory.getLogger(RedisUtil.class);

    /**
     * 可用连接实例的最大数目，默认值为8；
     * 如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
     */
    private final int MAX_ACTIVE = 512;

    /**
     * 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
     */
    private final int MAX_IDLE = 200;

    /**
     * 等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
     */
    private final int MAX_WAIT = 10000;

    private final int TIMEOUT = 10000;

    /**
     * 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
     */
    private static boolean TEST_ON_BORROW = true;

    private int dataBase = 0;

    private JedisPool pool = null;

    private String redisHost;

    private String redisPassword;

    private int redisPort;

    public RedisUtil(String redisHost, String redisPassword, int redisPort, int dataBase) {
        this.redisHost = redisHost;
        this.redisPassword = redisPassword;
        this.redisPort = redisPort;
        GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
        genericObjectPoolConfig.setMaxTotal(MAX_ACTIVE);
        genericObjectPoolConfig.setMaxIdle(MAX_IDLE);
        genericObjectPoolConfig.setMaxWaitMillis(MAX_WAIT);
        genericObjectPoolConfig.setTestOnBorrow(TEST_ON_BORROW);
        //初始化Redis连接池
        this.pool = new JedisPool(genericObjectPoolConfig, redisHost, redisPort, TIMEOUT, redisPassword);
        this.dataBase = dataBase;
        logger.info("----开启redis缓存(数据库为：" + dataBase + ")");
    }


    /**
     * 获取Jedis实例
     *
     * @return
     */
    public synchronized Jedis getJedis() {
        Jedis jedis = null;
        if (pool != null) {
            jedis = pool.getResource();
            jedis.select(dataBase);
        }
        return jedis;
    }

    /**
     * 释放jedis资源
     *
     * @param jedis
     */
    public static void returnResource(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }

    /**
     * 如果 key 已经持有其他值， SET 就覆写旧值，无视类型
     *
     * @param key
     * @param value
     */
    public boolean set(String key, String value) {
        boolean flag = false;
        Jedis jedis = this.getJedis();
        String result = jedis.set(key, value);
        if (result.equalsIgnoreCase("ok")) {
            flag = true;
        }
        jedis.close();
        return flag;
    }

    /**
     * @param key
     * @param seconds 设置失效时间
     * @param value
     * @return
     */
    public boolean setex(String key, int seconds, String value) {
        Jedis jedis = this.getJedis();
        String setex = jedis.setex(key, seconds, value);
        boolean result = false;
        if (setex.equalsIgnoreCase("ok")) {
            result = true;
        }
        jedis.close();
        return result;
    }

    /**
     * 返回key 所关联的字符串值。
     *
     * @param key
     * @return
     */
    public String get(String key) {
        Jedis jedis = this.getJedis();
        String result = jedis.get(key);
        jedis.close();
        return result;
    }

    /**
     * 检查给定 key 是否存在。
     *
     * @param key
     * @return
     */
    public boolean exist(String key) {
        Jedis j = this.getJedis();
        boolean res = j.exists(key);
        j.close();
        return res;
    }

    /***********************链表操作**************************/

    /**
     * 将一个或多个值 value 插入到列表 key 的表尾(最右边)
     *
     * @param key
     * @param values
     * @return
     */
    public boolean rpush(String key, String... values) {
        boolean flag = false;
        Jedis jedis = this.getJedis();
        Transaction multi = jedis.multi();
        Response<Long> rpush = multi.rpush(key, values);
        multi.exec();
        jedis.close();
        return flag;
    }

    /**
     * 将一个或多个值 value 插入到列表 key 的表尾(最左边)
     *
     * @param key
     * @param values
     * @return
     */
    public boolean lpush(String key, String... values) {
        boolean flag = false;
        Jedis jedis = this.getJedis();
        Transaction multi = jedis.multi();
        Long rpush = jedis.lpush(key, values);
        if (rpush > 0) {
            flag = true;
        }
        multi.exec();
        jedis.close();
        return flag;
    }

    /**
     * 返回列表 key 的长度。
     *
     * @param key
     * @return
     */
    public Long llen(String key) {
        Jedis jedis = this.getJedis();
        Long llen = jedis.llen(key);
        jedis.close();
        return llen;
    }

    /**
     * 将列表 key 下标为 index 的元素的值设置为 value 。
     *
     * @param mname
     * @param index
     * @param obj
     */
    public void lset(String mname, int index, Object obj) {
        Jedis jedis = this.getJedis();
        Transaction multi = jedis.multi();
        multi.lset(mname, index, JSON.toJSONString(obj));
        multi.exec();
        jedis.close();
    }

    /**
     * 删除链表中的某元素
     *
     * @param key
     * @param count -1=删除第一个  0=删除所有 任何一个正数=删除最先发现的n个
     * @param value
     */
    public void delList(String key, long count, String value) {
        Jedis jedis = this.getJedis();
        jedis.lrem(key, count, value);
        jedis.close();
    }

    /**
     * 得到整个List
     *
     * @param key
     * @return
     */
    public List<String> getList(String key) {
        List<String> list = null;
        Jedis j = this.getJedis();
        long len = j.llen(key);
        list = j.lrange(key, 0, len - 1);
        j.close();
        return list;
    }

    /**********************哈希操作**************************/

    /**
     * 将哈希表 key 中的域 field 的值设为 value 。
     *
     * @param key
     * @param field
     * @param value
     */
    public void hset(String key, String field, String value) {
        Jedis jedis = this.getJedis();
        jedis.hset(key, field, value);
        jedis.close();
    }

    /**
     * 返回哈希表 key 中给定域 field 的值。
     *
     * @param key
     * @param field
     * @return
     */
    public String hget(String key, String field) {
        Jedis jedis = this.getJedis();
        String hget = jedis.hget(key, field);
        jedis.close();
        return hget;
    }

    /**
     * 同时将多个 field-value (域-值)对设置到哈希表 key 中。
     *
     * @param key
     * @param map
     */
    public void hmset(String key, Map<String, String> map) {
        Jedis jedis = this.getJedis();
        jedis.hmset(key, map);
        jedis.close();
    }

    /**
     * 返回哈希表 key 中，一个或多个给定域的值。
     *
     * @param key
     * @param fields
     */
    public List<String> hmget(String key, String... fields) {
        Jedis jedis = this.getJedis();
        List<String> hmget = jedis.hmget(key, fields);
        jedis.close();
        return hmget;
    }

    public Map<String, String> hget(String key) {
        Jedis j = this.getJedis();
        Map<String, String> value = j.hgetAll(key);
        j.close();
        return value;
    }

    /**
     * 返回哈希表 key 中的所有域。
     *
     * @param key
     * @return
     */
    @SuppressWarnings("rawtypes")
    public Set hkeys(String key) {
        Jedis jedis = this.getJedis();
        Set<String> keys = jedis.keys(key);
        jedis.close();
        return keys;
    }

    /*******************移除操作************************/

    public void remove(String mname) {
        Jedis j = this.getJedis();
        Transaction tx = j.multi();
        tx.del(mname);
        tx.exec();
        j.close();
    }

    public void remove(String mname, String... keys) {
        Jedis jedis = this.getJedis();
        jedis.hdel(mname, keys);
        jedis.close();
    }

}
