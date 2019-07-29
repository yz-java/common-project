package com.yz.common.payment;

import com.yz.common.core.exception.HandlerException;
import com.yz.common.core.utils.HttpUtil;
import com.yz.common.payment.config.PayConfig;
import com.yz.common.payment.config.WXPayConfig;
import com.yz.common.payment.constants.WXPayConstant;
import com.yz.common.payment.enums.PayTypeEnum;
import com.yz.common.payment.order.query.OrderQueryResponse;
import com.yz.common.payment.order.query.WxOrderQueryResponse;
import com.yz.common.payment.trade.pay.TradePay;
import com.yz.common.payment.trade.pay.bo.TradePayResponse;
import com.yz.common.payment.trade.pay.bo.UserOrder;
import com.yz.common.payment.trade.pay.bo.WXTradePayResponse;
import com.yz.common.payment.trade.pay.builder.WXTradePayBuilder;
import com.yz.common.payment.trade.pay.builder.WXTradePayParamsBuilder;
import com.yz.common.payment.trade.pay.params.TradePayParams;
import com.yz.common.payment.utils.WXPayUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 微信支付实现
 * @author: yangzhao
 * @Date: 2019/7/10 19:20
 * @Description:
 */
public class WXPayServiceImpl implements PayService {


    @Override
    public PayConfig getPayConfig(Integer orderFrom) {
        return null;
    }

    @Override
    public TradePayResponse tradePayOrder(UserOrder userOrder, String notifyUrl, String returnUrl) throws Exception {
        Integer orderFrom = userOrder.getOrderFrom();

        WXPayConfig wxPayConfig = (WXPayConfig) getPayConfig(orderFrom);
        TradePay tradePay = WXTradePayBuilder.newInstance().payType(userOrder.getPayType()).wxPayConfig(wxPayConfig).build();
        WXTradePayParamsBuilder wxTradePayParamsBuilder = WXTradePayParamsBuilder.newInstance().notifyUrl(notifyUrl).userOrder(userOrder);

        if (PayTypeEnum.JS.getCode()==userOrder.getPayType().intValue()){
            //TODO 根据实际业务参数填写用户open_id
            String openId="";
            wxTradePayParamsBuilder.openId(openId);
        }
        TradePayParams tradePayParams = wxTradePayParamsBuilder.build();
        WXTradePayResponse wxTradePayResponse = (WXTradePayResponse) tradePay.createTradePay(tradePayParams);
        return wxTradePayResponse;
    }

    @Override
    public boolean refund(String orderNum) throws HandlerException {
        //TODO 根据实际业务参数填写
        Integer orderFrom=0;
        WXPayConfig wxPayConfig = (WXPayConfig) getPayConfig(orderFrom);
        Map<String,String> requestParams = new HashMap<>();
        requestParams.put("appid",wxPayConfig.getAppId());
        requestParams.put("mch_id",wxPayConfig.getMchId());
        requestParams.put("nonce_str", UUID.randomUUID().toString().replace("-",""));
        requestParams.put("out_trade_no",orderNum);
        //TODO 自行计算填写
        Double totalFee=0.0;
        //TODO 自行计算填写
        Double refundFee=0.0;
        requestParams.put("total_fee",totalFee.toString());
        requestParams.put("refund_fee",refundFee.toString());
        Map<String, String> resultMap = null;
        try {
            String sign = WXPayUtil.generateSignature(requestParams, wxPayConfig.getMchKey());
            requestParams.put("sign",sign);
            String xml = WXPayUtil.mapToXml(requestParams);
            String resultXml = HttpUtil.sendPost(WXPayConstant.DOMAIN_API + WXPayConstant.REFUND_URL_SUFFIX, xml);
            resultMap = WXPayUtil.xmlToMap(resultXml);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }

        if (resultMap.get("return_code").toString().equals("SUCCESS")&&resultMap.get("result_code").toString().equals("SUCCESS")){
            return true;
        }
        return false;
    }

    @Override
    public OrderQueryResponse orderQuery(String orderNum) throws HandlerException {
        //TODO 根据实际业务参数填写
        Integer orderFrom=0;
        WXPayConfig wxPayConfig = (WXPayConfig) getPayConfig(orderFrom);
        Map<String,String> requestParams = new HashMap<>();
        requestParams.put("appid",wxPayConfig.getAppId());
        requestParams.put("mch_id",wxPayConfig.getMchId());
        requestParams.put("nonce_str", UUID.randomUUID().toString().replace("-",""));
        requestParams.put("out_trade_no",orderNum);
        Map<String, String> resultMap = null;
        try {
            String sign = WXPayUtil.generateSignature(requestParams, wxPayConfig.getMchKey());
            requestParams.put("sign",sign);
            String xml = WXPayUtil.mapToXml(requestParams);
            String resultXml = HttpUtil.sendPost(WXPayConstant.DOMAIN_API + WXPayConstant.ORDERQUERY_URL_SUFFIX, xml);
            resultMap = WXPayUtil.xmlToMap(resultXml);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
        if (resultMap.get("return_code").toString().equals("SUCCESS")&&resultMap.get("result_code").toString().equals("SUCCESS")){
            WxOrderQueryResponse response = new WxOrderQueryResponse();
            response.setTradeType(resultMap.get("trade_type"));
            response.setTradeState(resultMap.get("trade_state"));
            response.setTotalFee(resultMap.get("total_fee"));
            response.setCashFee(resultMap.get("cash_fee"));
            response.setTransactionId(resultMap.get("transaction_id"));
            response.setOutTradeNo(resultMap.get("out_trade_no"));
            response.setTimeEnd(resultMap.get("time_end"));
            response.setTradeStateDesc(resultMap.get("trade_state_desc"));
            return response;
        }
        return null;
    }
}
