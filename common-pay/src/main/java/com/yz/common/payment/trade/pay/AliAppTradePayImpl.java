package com.yz.common.payment.trade.pay;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.yz.common.payment.config.AliPayConfig;
import com.yz.common.payment.trade.pay.bo.AliTradePayResponse;
import com.yz.common.payment.trade.pay.params.AliTradeAppPayParams;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: yangzhao
 * @Date: 2019/7/10 17:43
 * @Description:
 */
public class AliAppTradePayImpl implements TradePay<AliTradeAppPayParams, AliTradePayResponse> {

    private AliPayConfig aliPayConfig;

    public AliAppTradePayImpl(AliPayConfig aliPayConfig) {
        this.aliPayConfig = aliPayConfig;
    }

    @Override
    public AliTradePayResponse createTradePay(AliTradeAppPayParams aliTradeAppPayParams) throws Exception {
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",aliPayConfig.getAppId(),aliPayConfig.getAppPrivateKey(),"json",aliPayConfig.getCharset(),aliPayConfig.getAliPayPublicKey(),aliPayConfig.getSignType());
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        request.setNotifyUrl(aliTradeAppPayParams.getNotifyUrl());
        Map<String,Object> bizContent = new HashMap<>();
        bizContent.put("total_amount", aliTradeAppPayParams.getTotalAmount());
        bizContent.put("subject", aliTradeAppPayParams.getSubject());
        bizContent.put("out_trade_no", aliTradeAppPayParams.getOutTradeNo());
        request.setBizContent(JSON.toJSONString(bizContent));
        AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
        if(!response.isSuccess()){
            return null;
        }
        logger.info("支付宝APP统一下单成功");
        return AliTradePayResponse.generator(response);
    }
}
