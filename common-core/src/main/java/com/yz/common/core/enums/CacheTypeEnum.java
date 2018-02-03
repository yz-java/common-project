package com.yz.common.core.enums;

/**
 * Created by yangzhao on 18/1/17.
 */
public enum CacheTypeEnum {

    JVM(1),REDIS(2);

    private int type;

    private CacheTypeEnum(int type){
        this.type = type;
    }

    public int getType(){
        return this.type;
    }
}
