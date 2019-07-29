package com.yz.common.payment.trade.pay;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 统一下单
 * @author: yangzhao
 * @Date: 2019/7/10 11:38
 * @Description:
 */
public interface TradePay<TradePayParams,TradePayResponse> {

    public static final Logger logger = LoggerFactory.getLogger(TradePay.class);

    public TradePayResponse createTradePay(TradePayParams tradPayParams) throws Exception;
}
