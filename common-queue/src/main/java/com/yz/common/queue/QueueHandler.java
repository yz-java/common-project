package com.yz.common.queue;

/**
 * 订阅数据回调业务层
 * Created by yangzhao on 16/11/14.
 */
public interface QueueHandler {

    public void responseData(String message);
}
