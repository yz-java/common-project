package com.yz.common.payment.enums;

/**
 * @author: yangzhao
 * @Date: 2019/7/10 19:24
 * @Description:
 */
public enum PayTypeEnum {
    JS(1),
    APP(2),
    PC(3)
    ;
    private int code;

    PayTypeEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
