package com.yz.common.queue;

import com.yz.common.queue.jdk.JMessageQueue;
import com.yz.common.queue.redis.RMessageQueue;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by yangzhao on 16/8/28.
 */
@Component
public class IMessageQueueFactory {

    @Resource(name = "jMessageQueue")
    private JMessageQueue jMessageQueue;

    @Resource(name = "rMessageQueue")
    private RMessageQueue rMessageQueue;

    private IMessageQueue iMessageQueue = null;

    /**
     * 1=jdk默认实现 2=redis 3=kafka 4=rabbitmq
     * @param type
     */
    public void init(int type){
        switch (type){
            case 1 :
                iMessageQueue = jMessageQueue;
                break;
            case 2:
                iMessageQueue = rMessageQueue;
                break;
        }
    }

    public IMessageQueue getIMessageQueue(){
        return iMessageQueue;
    }
}
