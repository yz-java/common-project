package com.yz.common.queue.jdk;

import com.yz.common.queue.IMessageQueue;
import com.yz.common.queue.QueueHandler;
import com.yz.common.core.json.JacksonUtil;
import org.springframework.stereotype.Component;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 基于ArrayBlockingQueue实现消息队列
 * Created by yangzhao on 16/8/28.
 */
@Component("jMessageQueue")
public class JMessageQueue implements IMessageQueue {

    private final int BLOCKINGQUEUE_SIZE = 1000;

    private final ConcurrentHashMap<String,ArrayBlockingQueue> map = new ConcurrentHashMap();
    /**
     * 将指定的元素插入到此队列的尾部（如果立即可行且不会超过该队列的容量），在成功时返回 true，如果此队列已满，则抛出 IllegalStateException。
     * ★★★★★注意：业务层调用此API需要手动捕获IllegalStateException异常
     * @param o
     * @return
     */
    @Override
    public synchronized boolean publish (String channel, Object o){
        ArrayBlockingQueue queue = map.get(channel);
        if (queue == null){
            queue = new ArrayBlockingQueue(BLOCKINGQUEUE_SIZE,true);
        }
        queue.add(o);
        map.put(channel, queue);
        return true;
    }
    /**
     * 获取并移除此队列的头部，在元素变得可用之前一直等待（如果有必要）。
     * @param channel
     */
    @Override
    public synchronized void subscribe(String channel, QueueHandler queueHandler) {
        ArrayBlockingQueue queue = map.get(channel);
        if (queue==null){
            queue = new ArrayBlockingQueue(BLOCKINGQUEUE_SIZE,true);
            map.put(channel,queue);
        }
        final ArrayBlockingQueue abq = queue;
        new Thread(()->{
            while (true){
                Object take = null;
                try {
                    take = abq.take();
                    queueHandler.responseData(JacksonUtil.parse(take));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    /**
     * 将指定的元素插入此队列的尾部，如果该队列已满，则等待可用的空间。
     * @param o
     * @throws InterruptedException
     */
    @Override
    public synchronized void put(String channel, Object o) throws InterruptedException {
        ArrayBlockingQueue queue = map.get(channel);
        if (queue == null){
            queue = new ArrayBlockingQueue(BLOCKINGQUEUE_SIZE,true);
        }
        queue.put(o);
        map.put(channel,queue);
    }
}
