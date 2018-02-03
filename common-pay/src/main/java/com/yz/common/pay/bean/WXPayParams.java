package com.yz.common.pay.bean;


import com.yz.common.core.utils.UUIDUtil;

/**
 * Created by yangzhao on 17/8/8.
 */
public class WXPayParams extends WXRequestParams {
    //预支付订单id
    private String prepayId;
    //扩展字段 填写固定值Sign=WXPay
    private String pkg = "Sign=WXPay";

    private String noncestr = UUIDUtil.getUUID();

    public String getPrepayId() {
        return prepayId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }

    public String getPkg() {
        return pkg;
    }

    public String getNoncestr() {
        return noncestr;
    }

}
