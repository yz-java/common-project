package com.yz.common.payment.order.query;

/**
 * @author: yangzhao
 * @Date: 2019/7/11 20:00
 * @Description:
 */
public class WxOrderQueryResponse extends OrderQueryResponse {
    /**
     * 交易类型
     * JSAPI，NATIVE，APP，MICROPAY
     */
    private String tradeType;
    /**
     * 交易状态
     * SUCCESS—支付成功
     * REFUND—转入退款
     * NOTPAY—未支付
     * CLOSED—已关闭
     * REVOKED—已撤销（付款码支付）
     * USERPAYING--用户支付中（付款码支付）
     * PAYERROR--支付失败(其他原因，如银行返回失败)
     */
    private String tradeState;
    /**
     * 订单总金额
     */
    private String totalFee;
    /**
     * 现金支付金额
     */
    private String cashFee;
    /**
     * 微信支付订单号
     */
    private String transactionId;
    /**
     * 商户订单号
     */
    private String outTradeNo;
    /**
     * 支付完成时间
     * 订单支付时间，格式为yyyyMMddHHmmss
     */
    private String timeEnd;
    /**
     * 交易状态描述
     */
    private String tradeStateDesc;

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getTradeState() {
        return tradeState;
    }

    public void setTradeState(String tradeState) {
        this.tradeState = tradeState;
    }

    public String getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(String totalFee) {
        this.totalFee = totalFee;
    }

    public String getCashFee() {
        return cashFee;
    }

    public void setCashFee(String cashFee) {
        this.cashFee = cashFee;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public String getTradeStateDesc() {
        return tradeStateDesc;
    }

    public void setTradeStateDesc(String tradeStateDesc) {
        this.tradeStateDesc = tradeStateDesc;
    }
}
