package com.yz.common.payment.trade.pay.builder;


import com.yz.common.payment.config.AliPayConfig;
import com.yz.common.payment.enums.PayTypeEnum;
import com.yz.common.payment.trade.pay.AliAppTradePayImpl;
import com.yz.common.payment.trade.pay.TradePay;

/**
 * @author: yangzhao
 * @Date: 2019/7/10 19:52
 * @Description:
 */
public class AliTradePayBuilder {

    private AliPayConfig aliPayConfig;

    private Integer payType;

    public static AliTradePayBuilder builder(){
        return new AliTradePayBuilder();
    }

    public AliTradePayBuilder aliPayConfig(AliPayConfig aliPayConfig){
        this.aliPayConfig=aliPayConfig;
        return this;
    }

    public AliTradePayBuilder payType(Integer payType){
        this.payType=payType;
        return this;
    }

    public TradePay build(){
        TradePay tradePay=null;
        if (payType== PayTypeEnum.JS.getCode()){

        }else if (payType==PayTypeEnum.APP.getCode()){
            tradePay = new AliAppTradePayImpl(aliPayConfig);
        }else if (payType==PayTypeEnum.PC.getCode()){

        }
        return tradePay;
    }

}
