package com.yz.common.payment;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.yz.common.payment.config.AliPayConfig;
import com.yz.common.payment.config.PayConfig;
import com.yz.common.payment.order.query.OrderQueryResponse;
import com.yz.common.payment.trade.pay.TradePay;
import com.yz.common.payment.trade.pay.bo.AliTradePayResponse;
import com.yz.common.payment.trade.pay.bo.TradePayResponse;
import com.yz.common.payment.trade.pay.bo.UserOrder;
import com.yz.common.payment.trade.pay.builder.AliTradePayBuilder;
import com.yz.common.payment.trade.pay.builder.AliTradePayParamsBuilder;
import com.yz.common.payment.trade.pay.params.TradePayParams;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: yangzhao
 * @Date: 2019/7/10 19:18
 * @Description:
 */
public class AliPayServiceImpl implements PayService {


    @Override
    public PayConfig getPayConfig(Integer orderFrom) {
        return null;
    }

    @Override
    public TradePayResponse tradePayOrder(UserOrder userOrder, String notifyUrl, String returnUrl) throws Exception {
        AliPayConfig aliPayConfig= (AliPayConfig) getPayConfig(userOrder.getOrderFrom());
        TradePay tradePay = AliTradePayBuilder.builder().aliPayConfig(aliPayConfig).payType(userOrder.getPayType()).build();
        TradePayParams tradePayParams = AliTradePayParamsBuilder.builder().userOrder(userOrder).notifyUrl(notifyUrl).returnUrl(returnUrl).build();
        AliTradePayResponse tradePayResponse = (AliTradePayResponse) tradePay.createTradePay(tradePayParams);
        return tradePayResponse;
    }

    @Override
    public boolean refund(String orderNum) {
        //TODO 根据实际业务参数填写
        Integer orderFrom=0;
        AliPayConfig aliPayConfig= (AliPayConfig) getPayConfig(orderFrom);
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",aliPayConfig.getAppId(),aliPayConfig.getAppPrivateKey(),"json",aliPayConfig.getCharset(),aliPayConfig.getAliPayPublicKey(),"RSA2");
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        Map<String,String> bizContent = new HashMap<>();
        bizContent.put("out_trade_no",orderNum);
        String refundAmount="";
        bizContent.put("refund_amount",refundAmount);
        AlipayTradeRefundResponse response = null;
        try {
            response = alipayClient.execute(request);
            if(!response.isSuccess()){
                return false;
            }
        } catch (AlipayApiException e) {
            logger.error("调用支付宝退款失败 ====> "+e.getErrMsg());
        }
        return true;
    }

    @Override
    public OrderQueryResponse orderQuery(String orderNum) throws AlipayApiException {
        //TODO 根据实际业务参数填写
        Integer orderFrom=0;
        AliPayConfig aliPayConfig= (AliPayConfig) getPayConfig(orderFrom);
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",aliPayConfig.getAppId(),aliPayConfig.getAppPrivateKey(),"json",aliPayConfig.getCharset(),aliPayConfig.getAliPayPublicKey(),"RSA2");
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        request.setBizContent("{\"out_trade_no\":\""+orderNum+"\"}");
        AlipayTradeQueryResponse response = alipayClient.execute(request);
        if(!response.isSuccess()){
            return null;
        }
        return null;
    }
}
