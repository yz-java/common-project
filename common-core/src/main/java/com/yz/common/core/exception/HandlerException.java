package com.yz.common.core.exception;

/**
 * Created by yangzhao on 17/3/29.
 */
public class HandlerException extends Exception {

    private int code;

    public HandlerException(int code,String message) {
        super(String.valueOf(message));
        this.code = code;
    }
    public HandlerException(String message) {
        super(String.valueOf(message));
    }

    public String getErrorInfo(){
        return super.getMessage();
    }

    public int getCode(){
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
