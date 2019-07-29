package com.yz.common.payment.trade.pay;

import com.alibaba.fastjson.JSON;
import com.yz.common.core.exception.HandlerException;
import com.yz.common.core.utils.HttpUtil;
import com.yz.common.core.utils.SnowflakeIdWorker;
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
public class WXJsApiTradePayImpl implements TradePay<WXTradeJsApiPayParams, WXTradePayResponse> {

    private WXPayConfig wxPayConfig;

    public WXJsApiTradePayImpl(WXPayConfig wxPayConfig) {
        this.wxPayConfig = wxPayConfig;
    }

    @Override
    public WXTradePayResponse createTradePay(WXTradeJsApiPayParams wxTradeJsApiPayParams) throws HandlerException {
        logger.info("微信 trade js api 参数："+ JSON.toJSONString(wxTradeJsApiPayParams));
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
        requestParams.put("openid", wxTradeJsApiPayParams.getOpenId());
        requestParams.put("sign_type", WXPayConfig.SignType.MD5.toString());
        Map<String, String> resultMap = null;
        try {
            String sign = WXPayUtil.generateSignature(requestParams, wxPayConfig.getMchKey());
            requestParams.put("sign",sign);
            String xml = WXPayUtil.mapToXml(requestParams);
            String resultXml = HttpUtil.sendPost("https://"+ WXPayConstant.DOMAIN_API + WXPayConstant.UNIFIEDORDER_URL_SUFFIX, xml);
            resultMap = WXPayUtil.xmlToMap(resultXml);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (resultMap.get("return_code").equals("SUCCESS")&&resultMap.get("result_code").equals("SUCCESS")){
            WXTradePayResponse wxTradePayResponse = new WXTradePayResponse();
            String trade_type = resultMap.get("trade_type");
            wxTradePayResponse.setTradeType(trade_type);
            String prepay_id = "prepay_id="+resultMap.get("prepay_id");
            wxTradePayResponse.setPrepayId(prepay_id);
            String appid = resultMap.get("appid");
            wxTradePayResponse.setAppId(appid);
//            String nonce_str = resultMap.get("nonce_str");
//            wxTradePayResponse.setNonceStr(nonce_str);
            //默认为md5签名
            wxTradePayResponse.setSignType(WXPayConfig.SignType.MD5.toString());

            String timeStamp = String.valueOf(System.currentTimeMillis() / 1000L);

            Map<String,String> jsSdkPayParams=new HashMap<>();
            jsSdkPayParams.put("appId",wxPayConfig.getAppId());
            jsSdkPayParams.put("timeStamp",timeStamp);
            String nonce_str = SnowflakeIdWorker.generateId().toString();
            jsSdkPayParams.put("nonceStr",nonce_str);
            jsSdkPayParams.put("package",prepay_id);
            jsSdkPayParams.put("signType",WXPayConfig.SignType.MD5.toString());
            try {
                String sign = WXPayUtil.generateSignature(jsSdkPayParams, wxPayConfig.getMchKey());
                wxTradePayResponse.setTimeStamp(timeStamp);
                wxTradePayResponse.setNonceStr(nonce_str);
                wxTradePayResponse.setSign(sign);
                return wxTradePayResponse;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
