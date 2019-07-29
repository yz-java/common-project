package com.yz.common.payment.trade.pay;

import com.yz.common.core.utils.HttpUtil;
import com.yz.common.payment.config.WXPayConfig;
import com.yz.common.payment.constants.WXPayConstant;
import com.yz.common.payment.trade.pay.bo.WXTradePayResponse;
import com.yz.common.payment.trade.pay.params.WXTradeJsApiPayParams;
import com.yz.common.payment.utils.WXPayUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author: yangzhao
 * @Date: 2019/7/10 14:44
 * @Description:
 */
public class WXTradeAppPayImpl implements TradePay<WXTradeJsApiPayParams, WXTradePayResponse> {

    private WXPayConfig wxPayConfig;

    public WXTradeAppPayImpl(WXPayConfig wxPayConfig) {
        this.wxPayConfig = wxPayConfig;
    }

    @Override
    public WXTradePayResponse createTradePay(WXTradeJsApiPayParams wxTradeJsApiPayParams) {
        Map<String,String> requestParams = new HashMap<>();
        requestParams.put("appid",wxPayConfig.getAppId());
        requestParams.put("mch_id",wxPayConfig.getMchId());
        requestParams.put("nonce_str", UUID.randomUUID().toString().replace("-",""));
        requestParams.put("body", wxTradeJsApiPayParams.getBody());
        requestParams.put("out_trade_no", wxTradeJsApiPayParams.getOutTradeNo());
        requestParams.put("total_fee", wxTradeJsApiPayParams.getTotalFee().toString());
        requestParams.put("spbill_create_ip", wxTradeJsApiPayParams.getSpbillCreateIp());
        requestParams.put("notify_url", wxTradeJsApiPayParams.getNotifyUrl());
        requestParams.put("trade_type", wxTradeJsApiPayParams.getTradeType());
        Map<String, String> resultMap = null;
        try {
            String sign = WXPayUtil.generateSignature(requestParams, wxPayConfig.getMchKey());
            requestParams.put("sign",sign);
            String xml = WXPayUtil.mapToXml(requestParams);
            String resultXml = HttpUtil.sendPost(WXPayConstant.DOMAIN_API + WXPayConstant.UNIFIEDORDER_URL_SUFFIX, xml);
            resultMap = WXPayUtil.xmlToMap(resultXml);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (resultMap.get("return_code").equals("SUCCESS")&&resultMap.get("result_code").equals("SUCCESS")){
            WXTradePayResponse wxTradePayResponse = new WXTradePayResponse();
            wxTradePayResponse.setTradeType(resultMap.get("trade_type"));
            wxTradePayResponse.setPrepayId(resultMap.get("prepay_id"));
            return wxTradePayResponse;
        }
        return null;
    }
}
