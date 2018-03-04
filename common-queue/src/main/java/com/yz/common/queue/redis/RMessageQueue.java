package com.yz.common.queue.redis;

import com.yz.common.queue.IMessageQueue;
import com.yz.common.queue.QueueHandler;
import com.yz.common.core.json.JSON;
import com.yz.common.core.redis.RedisUtil;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import javax.annotation.Resource;

/**
 * 基于redis实现消息队列
 * Created by yangzhao on 16/11/14.
 */
@Component("rMessageQueue")
public class RMessageQueue implements IMessageQueue {

    @Resource
    private RedisUtil redisUtil;

    @Override
    public boolean publish(String channel, Object o) {
        Jedis jedis = redisUtil.getJedis();
        String jsonString = JSON.getInterface().toJsonString(o);
        Long publisher = jedis.publish(channel,jsonString);
        logger.info("订阅该频道的人数：" + publisher);
        jedis.close();
        return true;
    }

    @Override
    public void subscribe(String channel, QueueHandler queueHandler) throws Exception {
        Jedis jedis = redisUtil.getJedis();
        jedis.subscribe(new JedisPubSub() {
            @Override
            public void onMessage(String channel, String message) {
                queueHandler.responseData(message);
            }
        }, channel);
//		    jedis.close();
    }

    @Override
    public void put(String channel, Object o) throws InterruptedException {
        this.publish(channel, o);
    }
}
