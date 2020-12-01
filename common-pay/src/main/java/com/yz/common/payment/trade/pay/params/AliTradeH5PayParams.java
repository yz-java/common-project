package com.yz.common.payment.trade.pay.params;

/**
 * @author: yangzhao
 * @Date: 2019/7/10 11:58
 * @Description:
 */
public class AliTradeH5PayParams extends AliTradePayParams {

    /**
     * 用户付款中途退出返回商户网站的地址
     */
    private String quitUrl;
    /**
     * 销售产品码，商家和支付宝签约的产品码
     */
    private String product_code;

    private String notifyUrl;

    public String getQuitUrl() {
        return quitUrl;
    }

    public void setQuitUrl(String quitUrl) {
        this.quitUrl = quitUrl;
    }

    public String getProduct_code() {
        return product_code;
    }

    public void setProduct_code(String product_code) {
        this.product_code = product_code;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }
}
