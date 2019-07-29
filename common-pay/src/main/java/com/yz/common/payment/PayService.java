package com.yz.common.payment;

import com.alipay.api.AlipayApiException;
import com.yz.common.core.exception.HandlerException;
import com.yz.common.payment.config.PayConfig;
import com.yz.common.payment.order.query.OrderQueryResponse;
import com.yz.common.payment.trade.pay.bo.TradePayResponse;
import com.yz.common.payment.trade.pay.bo.UserOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author: yangzhao
 * @Date: 2019/7/10 11:01
 * @Description:
 */
public interface PayService {

    public static final Logger logger = LoggerFactory.getLogger(PayService.class);

    /**
     * 通过订单来源获取支付配置信息
     * @param orderFrom
     * @return
     */
    public PayConfig getPayConfig(Integer orderFrom);

    /**
     * 统一支付下单
     * @param userOrder
     * @param notifyUrl 回调url
     * @param returnUrl 返回页面url
     * @return
     * @throws Exception
     */
    public TradePayResponse tradePayOrder(UserOrder userOrder, String notifyUrl, String returnUrl) throws Exception;

    /**
     * 通过订单号退款
     * @param orderNum
     */
    public boolean refund(String orderNum) throws HandlerException;

    /**
     * 订单查询
     * @param orderNum
     * @return
     */
    public OrderQueryResponse orderQuery(String orderNum) throws AlipayApiException, HandlerException;

}
