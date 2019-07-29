package com.yz.common.payment.trade.pay.builder;


import com.yz.common.payment.config.WXPayConfig;
import com.yz.common.payment.enums.PayTypeEnum;
import com.yz.common.payment.trade.pay.TradePay;
import com.yz.common.payment.trade.pay.WXJsApiTradePayImpl;

/**
 * @author: yangzhao
 * @Date: 2019/7/10 19:52
 * @Description:
 */
public class WXTradePayBuilder {

    private Integer payType;

    private WXPayConfig wxPayConfig;

    public static WXTradePayBuilder newInstance(){
        return new WXTradePayBuilder();
    }

    public WXTradePayBuilder payType(int payType){
        this.payType=payType;
        return this;
    }

    public WXTradePayBuilder wxPayConfig(WXPayConfig wxPayConfig){
        this.wxPayConfig=wxPayConfig;
        return this;
    }

    public TradePay build(){
        TradePay tradePay=null;
        if (payType== PayTypeEnum.JS.getCode()){
            tradePay=new WXJsApiTradePayImpl(wxPayConfig);
        }else if (payType==PayTypeEnum.APP.getCode()){

        }else if (payType==PayTypeEnum.PC.getCode()){

        }
        return tradePay;
    }
}
