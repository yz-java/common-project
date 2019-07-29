package com.yz.common.payment.trade.pay.params;

/**
 * 蚂蚁金服APP统一下单参数
 * @author: yangzhao
 * @Date: 2019/7/10 14:15
 * @Description:
 */
public class AliTradeAppPayParams extends AliTradePayParams {
    /**
     * 支付宝服务器主动通知商户服务器
     */
    private String notifyUrl;

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }
}
