package com.yz.common.pay.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface IPay {

	public final Logger logger = LoggerFactory.getLogger(IPay.class);

	/**
	 * 创建订单
	 * @param t
	 */
	public <T> Object createOrder(T t);
	/**
	 * 退款
	 * @param
	 */
	public <T> Object refund(T t) throws Exception;
	/**
	 * 生成客户端请求支付的参数
	 * @param t
	 * @return
	 */
	public <T> Object createPayParams(T t);

	/**
	 * 查询订单状态
	 * @param orderId
	 * @return
	 */
	public Object queryOrderStatus(String orderId);
}
