package com.yz.common.payment.trade.pay.builder;

import com.yz.common.payment.enums.PayTypeEnum;
import com.yz.common.payment.trade.pay.bo.UserOrder;
import com.yz.common.payment.trade.pay.params.AliTradeAppPayParams;
import com.yz.common.payment.trade.pay.params.AliTradeH5PayParams;
import com.yz.common.payment.trade.pay.params.TradePayParams;

/**
 * @author: yangzhao
 * @Date: 2019/7/11 17:06
 * @Description:
 */
public class AliTradePayParamsBuilder {

    private UserOrder userOrder;

    private String notifyUrl;

    private String returnUrl;

    public static AliTradePayParamsBuilder builder() {
        return new AliTradePayParamsBuilder();
    }

    public AliTradePayParamsBuilder userOrder(UserOrder userOrder) {
        this.userOrder = userOrder;
        return this;
    }

    public AliTradePayParamsBuilder notifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
        return this;
    }

    public AliTradePayParamsBuilder returnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
        return this;
    }

    public TradePayParams build() {
        TradePayParams tradePayParams = null;
        Integer payType = userOrder.getPayType();
        if (payType == PayTypeEnum.JS.getCode()) {
            AliTradeH5PayParams aliTradeH5PayParams = new AliTradeH5PayParams();
            aliTradeH5PayParams.setBody(userOrder.getSubject());
            aliTradeH5PayParams.setOutTradeNo(userOrder.getOrderNum());
            aliTradeH5PayParams.setSubject(userOrder.getSubject());
            aliTradeH5PayParams.setTotalAmount(userOrder.getActualPrice().toString());
            aliTradeH5PayParams.setMethod("alipay.trade.wap.pay");
            aliTradeH5PayParams.setNotifyUrl(notifyUrl);
            aliTradeH5PayParams.setQuitUrl(returnUrl);
            tradePayParams = aliTradeH5PayParams;
        } else if (payType == PayTypeEnum.APP.getCode()) {
            AliTradeAppPayParams aliTradeAppPayParams = new AliTradeAppPayParams();
            aliTradeAppPayParams.setBody(userOrder.getSubject());
            aliTradeAppPayParams.setOutTradeNo(userOrder.getOrderNum());
            aliTradeAppPayParams.setSubject(userOrder.getSubject());
            aliTradeAppPayParams.setTotalAmount(userOrder.getActualPrice().toString());
            aliTradeAppPayParams.setMethod("alipay.trade.app.pay");
            aliTradeAppPayParams.setNotifyUrl(notifyUrl);
            tradePayParams = aliTradeAppPayParams;
        } else if (payType == PayTypeEnum.PC.getCode()) {

        }
        return tradePayParams;
    }
}
