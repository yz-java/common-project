package com.yz.common.payment.enums;

/**
 * @author: yangzhao
 * @Date: 2019/7/15 15:49
 * @Description:
 */
public enum PayChannelEnum {

    WX_PAY(1),
    ALI_PAY(2);

    private int code;

    PayChannelEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
