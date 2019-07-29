package com.yz.common.payment.trade.pay.bo;

/**
 * @author: yangzhao
 * @Date: 2019/7/10 14:46
 * @Description:
 */
public class WXTradePayResponse extends TradePayResponse {
    /**
     * 公众号 ID
     */
    private String appId;
    /**
     * 随机字符串
     */
    private String nonceStr;
    /**
     * 签名类型
     */
    private String signType;
    /**
     * 签名
     */
    private String sign;

    /**
     * 交易类型
     * JSAPI -JSAPI支付
     * NATIVE -Native支付
     * APP -APP支付
     */
    private String tradeType;
    /**
     * 预支付交易会话标识
     */
    private String prepayId;
    /**
     * 二维码链接
     */
    private String codeUrl;

    private String timeStamp;

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getPrepayId() {
        return prepayId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }

    public String getCodeUrl() {
        return codeUrl;
    }

    public void setCodeUrl(String codeUrl) {
        this.codeUrl = codeUrl;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
