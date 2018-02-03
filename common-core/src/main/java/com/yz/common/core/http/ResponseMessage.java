package com.yz.common.core.http;

/**
 * http请求返回消息
 * Created by yangzhao on 17/7/7.
 */
public class ResponseMessage {

    public ResponseMessage() {}

    public ResponseMessage(int status) {
        this.status = status;
    }

    public ResponseMessage(int status, Object data) {
        this.status = status;
        this.data = data;
    }

    public ResponseMessage(int status, String errorInfo) {
        this.status = status;
        this.errorInfo = errorInfo;
    }

    private int status;

    private Object data;

    private String errorInfo;

    public void setStatus(int status) {
        this.status = status;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public void setInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }


    public int getStatus() {
        return status;
    }

    public Object getData() {
        return data;
    }

    public String getInfo() {
        return errorInfo;
    }

    /**
     * 成功 无返回数据
     * @return
     */
    public static ResponseMessage success(){
        return new ResponseMessage(0,null);
    }

    /**
     * 成功
     * @param data 返回数据
     * @return
     */
    public static ResponseMessage success(Object data){
        return new ResponseMessage(0,data);
    }

    /**
     * 失败
     * @param errorCode 错误码
     * @return
     */
    public static ResponseMessage error(int errorCode){
        return new ResponseMessage(errorCode);
    }

    /**
     * 失败
     * @param errorCode 错误码
     * @param errorInfo 错误信息
     * @return
     */
    public static ResponseMessage error(int errorCode,String errorInfo){
        return new ResponseMessage(errorCode,errorInfo);
    }
}
