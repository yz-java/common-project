package com.yz.common.core.enums;

/**
 * Created by yangzhao on 18/1/17.
 */
public enum PayEnum {

    WEIXIN(1),ALI(2);

    private int type;

    private PayEnum(int type){
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
