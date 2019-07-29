package com.yz.common.payment.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: yangzhao
 * @Date: 2019/7/11 20:05
 * @Description:
 */
public enum WXTradeState {
    //支付成功
    SUCCESS,
    //转入退款
    REFUND,
    //未支付
    NOTPAY,
    //已关闭
    CLOSED,
    //已撤销（付款码支付）
    REVOKED,
    //用户支付中（付款码支付）
    USERPAYING,
    //支付失败(其他原因，如银行返回失败)
    PAYERROR;

    private static final Map<String, WXTradeState> lookup = new HashMap<String, WXTradeState>();

    static {
        for (WXTradeState d : WXTradeState.values()) {
            lookup.put(d.name(), d);
        }
    }

    public static WXTradeState get(String abbr){
        return lookup.get(abbr);
    }
}
