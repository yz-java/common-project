package com.yz.common.core.enums;

/**
 * Created by yangzhao on 18/1/17.
 */
public enum JsonTypeEnum {

    FASTJSON(1),JACKSON(2);

    private int type;

    private JsonTypeEnum(int type){
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
