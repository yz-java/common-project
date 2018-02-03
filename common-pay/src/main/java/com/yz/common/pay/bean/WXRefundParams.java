package com.yz.common.pay.bean;

/**
 * Created by yangzhao on 17/8/8.
 */
public class WXRefundParams extends WXRequestParams {

    //商户订单号
    private String outTradeNo;
    //商户退款单号
    private String outRefundNo;
    //订单金额
    private double totalFee;
    //退款金额
    private double refundFee;

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getOutRefundNo() {
        return outRefundNo;
    }

    public void setOutRefundNo(String outRefundNo) {
        this.outRefundNo = outRefundNo;
    }

    public double getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(double totalFee) {
        this.totalFee = totalFee;
    }

    public double getRefundFee() {
        return refundFee;
    }

    public void setRefundFee(double refundFee) {
        this.refundFee = refundFee;
    }
}
