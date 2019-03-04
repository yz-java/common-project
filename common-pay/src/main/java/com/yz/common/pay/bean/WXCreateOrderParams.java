package com.yz.common.pay.bean;

import com.yz.common.pay.util.WXPayUtil;

import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * Created by yangzhao on 17/8/8.
 */
public class WXCreateOrderParams extends WXRequestParams {

    //商品描述
    @NotNull
    private String body = "";
    //附加参数
    private Map<String,String> attach;
    //商户订单号
    @NotNull
    private String outTradeNo;
    //总金额
    @NotNull
    private double totalFee;
    //终端IP
    @NotNull
    private String spbillCreateIp;
    /**
     * 交易结束时间
     * 订单失效时间，格式为yyyyMMddHHmmss，如2009年12月27日9点10分10秒表示为20091227091010。其他详见 <a href="https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=4_2">时间规则</a>
     */
    private String timeExpire;

    @NotNull
    private String notifyUrl;

    private String tradeType = "APP";

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Map<String, String> getAttach() {
        return attach;
    }

    public void setAttach(Map<String, String> attach) {
        this.attach = attach;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public double getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(double totalFee) {
        this.totalFee = totalFee;
    }

    public String getSpbillCreateIp() {
        return spbillCreateIp;
    }

    public void setSpbillCreateIp(String spbillCreateIp) {
        this.spbillCreateIp = spbillCreateIp;
    }

    public String getTimeExpire() {
        return timeExpire;
    }

    public void setTimeExpire(String timeExpire) {
        this.timeExpire = timeExpire;
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
