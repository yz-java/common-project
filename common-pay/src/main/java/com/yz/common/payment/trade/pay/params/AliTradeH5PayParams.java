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

}
