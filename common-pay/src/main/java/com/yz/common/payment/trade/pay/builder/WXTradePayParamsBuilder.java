package com.yz.common.payment.trade.pay.builder;

import com.yz.common.payment.enums.PayTypeEnum;
import com.yz.common.payment.trade.pay.bo.UserOrder;
import com.yz.common.payment.trade.pay.params.TradePayParams;
import com.yz.common.payment.trade.pay.params.WXTradeAppPayParams;
import com.yz.common.payment.trade.pay.params.WXTradeJsApiPayParams;
import org.apache.commons.lang3.StringUtils;

/**
 * @author: yangzhao
 * @Date: 2019/7/10 19:57
 * @Description:
 */
public class WXTradePayParamsBuilder {

    private UserOrder userOrder;

    private String openId;

    private String notifyUrl;

    public static WXTradePayParamsBuilder newInstance(){
        return new WXTradePayParamsBuilder();
    }

    public WXTradePayParamsBuilder userOrder(UserOrder userOrder){
        this.userOrder=userOrder;
        return this;
    }

    public WXTradePayParamsBuilder notifyUrl(String notifyUrl){
        this.notifyUrl=notifyUrl;
        return this;
    }

    public WXTradePayParamsBuilder  openId(String openId){
        this.openId=openId;
        return this;
    }

    public TradePayParams build() throws Exception {
        TradePayParams tradePayParams=null;
        Integer payType=userOrder.getPayType();
        if (payType== PayTypeEnum.JS.getCode()){

            if (StringUtils.isEmpty(openId)){
                throw new Exception("OPENID IS NOT NULL OR EMPTY");
            }

            WXTradeJsApiPayParams wxTradeJsApiPayParams =new WXTradeJsApiPayParams();
            wxTradeJsApiPayParams.setOpenId(openId);
            wxTradeJsApiPayParams.setBody(userOrder.getSubject());
            wxTradeJsApiPayParams.setNotifyUrl(notifyUrl);
            wxTradeJsApiPayParams.setOutTradeNo(userOrder.getOrderNum());
            wxTradeJsApiPayParams.setTotalFee((long)(userOrder.getActualPrice().doubleValue()*100));
            wxTradeJsApiPayParams.setTradeType("JSAPI");
            wxTradeJsApiPayParams.setSpbillCreateIp("127.0.0.1");
            tradePayParams= wxTradeJsApiPayParams;
        }else if (payType==PayTypeEnum.APP.getCode()){
            WXTradeAppPayParams wxTradeAppPayParams =new WXTradeAppPayParams();
            wxTradeAppPayParams.setBody(userOrder.getSubject());
            wxTradeAppPayParams.setNotifyUrl(notifyUrl);
            wxTradeAppPayParams.setOutTradeNo(userOrder.getOrderNum());
            wxTradeAppPayParams.setTotalFee((long)userOrder.getActualPrice().doubleValue()*100);
            wxTradeAppPayParams.setTradeType("APP");
            tradePayParams= wxTradeAppPayParams;
        }else if (payType==PayTypeEnum.PC.getCode()){

        }
        return tradePayParams;
    }



}
