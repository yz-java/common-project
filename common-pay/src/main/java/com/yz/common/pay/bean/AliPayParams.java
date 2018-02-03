package com.yz.common.pay.bean;


import com.yz.common.pay.util.AlipayUtil;

/**
 * Created by yangzhao on 17/8/8.
 */
public class AliPayParams extends AliRequestParams {

    private String method = "alipay.trade.app.pay";

    private String version = "1.0";

    private String notifyUrl = AlipayUtil.pay_notify_url;

    //该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点， 如 1.5h，可转换为 90m
    private String timeoutExpress = "1d";
    //销售产品码，商家和支付宝签约的产品码，为固定值QUICK_MSECURITY_PAY
    private String productCode = "QUICK_MSECURITY_PAY";

    private double totalAmount = 0;
    //商品的标题/交易标题/订单标题/订单关键字等。示例值:大乐透
    private String subject;
    //对一笔交易的具体描述信息。如果是多种商品，请将商品描述字符串累加传给body。示例值：Iphone6 16G
    private String body;
    //商户唯一订单号
    private String outTradeNo;


    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

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

    public String getMethod() {
        return method;
    }

    public String getVersion() {
        return version;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public String getTimeoutExpress() {
        return timeoutExpress;
    }

    public String getProductCode() {
        return productCode;
    }


}
