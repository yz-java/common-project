package com.yz.common.core.exception;

/**
 * Created by yangzhao on 17/3/29.
 */
public class HandlerException extends Exception {

    public HandlerException(int message) {
        super(String.valueOf(message));
    }
    public HandlerException(String message) {
        super(String.valueOf(message));
    }

    public String getErrorInfo(){
        return super.getMessage();
    }
}
