package com.yz.common.payment.trade.pay.params;

/**
 * @author: yangzhao
 * @Date: 2019/7/10 11:58
 * @Description:
 */
public class AliTradePayParams extends TradePayParams {
    /**
     *接口名称
     */
    private String method;

    /**
     * 订单总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]
     */
    private String totalAmount;
    /**
     * 对一笔交易的具体描述信息。如果是多种商品，请将商品描述字符串累加传给body。
     */
    private String body;
    /**
     * 商品的标题/交易标题/订单标题/订单关键字等
     */
    private String subject;
    /**
     * 商户唯一订单号 最大长度64
     */
    private String outTradeNo;

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
