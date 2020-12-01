package com.yz.common.payment.trade.pay.bo;

import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradeWapPayResponse;

/**
 * @author: yangzhao
 * @Date: 2019/7/23 15:10
 * @Description:
 */
public class AliTradePayResponse extends TradePayResponse {

    /**
     * 商户原始订单号
     */
    private String merchantOrderNo;
    /**
     * 商户网站唯一订单号
     */
    private String outTradeNo;
    /**
     * 收款支付宝账号对应支付宝唯一用户号
     */
    private String sellerId;
    /**
     * 订单总金额
     */
    private String totalAmount;
    /**
     * 支付宝系统交易流水号
     */
    private String tradeNo;

    public String getMerchantOrderNo() {
        return merchantOrderNo;
    }

    public void setMerchantOrderNo(String merchantOrderNo) {
        this.merchantOrderNo = merchantOrderNo;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public static AliTradePayResponse generator(AlipayTradeAppPayResponse alipayTradeAppPayResponse) {
        AliTradePayResponse aliTradePayResponse = new AliTradePayResponse();
        aliTradePayResponse.setOutTradeNo(alipayTradeAppPayResponse.getOutTradeNo());
        aliTradePayResponse.setTotalAmount(alipayTradeAppPayResponse.getTotalAmount());
        aliTradePayResponse.setTradeNo(alipayTradeAppPayResponse.getOutTradeNo());
        aliTradePayResponse.setMerchantOrderNo(alipayTradeAppPayResponse.getMerchantOrderNo());
        aliTradePayResponse.setSellerId(alipayTradeAppPayResponse.getSellerId());
        return null;
    }

    public static AliTradePayResponse generator(AlipayTradeWapPayResponse alipayTradeAppPayResponse) {
        AliTradePayResponse aliTradePayResponse = new AliTradePayResponse();
        aliTradePayResponse.setOutTradeNo(alipayTradeAppPayResponse.getOutTradeNo());
        aliTradePayResponse.setTotalAmount(alipayTradeAppPayResponse.getTotalAmount());
        aliTradePayResponse.setTradeNo(alipayTradeAppPayResponse.getOutTradeNo());
        aliTradePayResponse.setSellerId(alipayTradeAppPayResponse.getSellerId());
        aliTradePayResponse.setBody(alipayTradeAppPayResponse.getBody());
        return aliTradePayResponse;
    }

}
