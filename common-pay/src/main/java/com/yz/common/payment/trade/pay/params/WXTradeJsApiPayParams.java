package com.yz.common.payment.trade.pay.params;

/**
 * @author: yangzhao
 * @Date: 2019/7/10 19:00
 * @Description:
 */
public class WXTradeJsApiPayParams extends WXTradePayParams {

    private String openId;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}
