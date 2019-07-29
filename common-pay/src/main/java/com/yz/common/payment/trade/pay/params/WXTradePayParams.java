package com.yz.common.payment.trade.pay.params;

/**
 * @author: yangzhao
 * @Date: 2019/7/10 11:41
 * @Description:
 */
public class WXTradePayParams extends TradePayParams {
    /**
     * 商品描述
     */
    private String body;
    /**
     * 商户订单号
     */
    private String outTradeNo;
    /**
     * 订单总金额，单位为分
     */
    private Long totalFee;
    /**
     * 终端IP 支持IPV4和IPV6两种格式的IP地址。用户的客户端IP
     */
    private String spbillCreateIp;
    /**
     * 异步接收微信支付结果通知的回调地址
     */
    private String notifyUrl;
    /**
     * 交易类型
     *
     * JSAPI -JSAPI支付
     * NATIVE -Native支付
     * APP -APP支付
     */
    private String tradeType;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public Long getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Long totalFee) {
        this.totalFee = totalFee;
    }

    public String getSpbillCreateIp() {
        return spbillCreateIp;
    }

    public void setSpbillCreateIp(String spbillCreateIp) {
        this.spbillCreateIp = spbillCreateIp;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }
}
