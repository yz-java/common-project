package com.yz.common.payment.trade.pay;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import com.yz.common.payment.config.AliPayConfig;
import com.yz.common.payment.trade.pay.bo.AliTradePayResponse;
import com.yz.common.payment.trade.pay.params.AliTradeH5PayParams;

/**
 * @author: yangzhao
 * @Date: 2019/7/10 17:43
 * @Description:
 */
public class AliH5TradePayImpl implements TradePay<AliTradeH5PayParams, AliTradePayResponse> {

    private AliPayConfig aliPayConfig;

    public AliH5TradePayImpl(AliPayConfig aliPayConfig) {
        this.aliPayConfig = aliPayConfig;
    }

    @Override
    public AliTradePayResponse createTradePay(AliTradeH5PayParams aliTradeH5PayParams) throws Exception {
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",
            aliPayConfig.getAppId(), aliPayConfig.getAppPrivateKey(), "json", aliPayConfig.getCharset(),
            aliPayConfig.getAliPayPublicKey(), aliPayConfig.getSignType());
        AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();
        request.setNotifyUrl(aliTradeH5PayParams.getNotifyUrl());
        request.setReturnUrl(aliTradeH5PayParams.getQuitUrl());
        Map<String, Object> bizContent = new HashMap<>();
        BigDecimal bigDecimal = new BigDecimal(aliTradeH5PayParams.getTotalAmount());
        // BigDecimal bigDecimal = new BigDecimal("0.01");
        bigDecimal = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
        bizContent.put("total_amount", bigDecimal.toString());
        bizContent.put("subject", aliTradeH5PayParams.getSubject());
        bizContent.put("out_trade_no", aliTradeH5PayParams.getOutTradeNo());
        bizContent.put("quit_url", aliTradeH5PayParams.getQuitUrl());
        bizContent.put("product_code", "QUICK_WAP_WAY");
        request.setBizContent(JSON.toJSONString(bizContent));
        AlipayTradeWapPayResponse alipayTradeWapPayResponse = alipayClient.pageExecute(request);
        if (!alipayTradeWapPayResponse.isSuccess()) {
            return null;
        }
        logger.info("支付宝H5统一下单成功");
        return AliTradePayResponse.generator(alipayTradeWapPayResponse);
    }
}
