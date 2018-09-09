package com.yz.common.core.message;

/**
 * http请求返回消息
 * Created by yangzhao on 17/7/7.
 */
public class ResponseMessage {

    public ResponseMessage() {}

    public ResponseMessage(int code, int errorCode, Object data, String errorMessage) {
        this.code = code;
        this.errorCode = errorCode;
        this.data = data;
        this.errorMessage = errorMessage;
    }

    public ResponseMessage(int code) {
        this.code = code;
    }

    /**
     *  构造函数
     * @param data 正确返回数据
     */
    public ResponseMessage(Object data) {
        this.data = data;
    }

    /**
     * 构造函数
     * @param errorCode 错误状态码
     * @param errorMessage 错误信息
     */
    public ResponseMessage(int errorCode, String errorMessage) {
        this.errorCode=errorCode;
        this.errorMessage = errorMessage;
    }

    private int code = 10000;

    private int errorCode;

    private Object data;

    private String errorMessage;

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * 成功 无返回数据
     * @return
     */
    public static ResponseMessage success(){
        return new ResponseMessage();
    }

    /**
     * 成功
     * @param data 返回数据
     * @return
     */
    public static ResponseMessage success(Object data){
        return new ResponseMessage(data);
    }

    /**
     * 失败
     * @param errorCode 错误码
     * @param errorMessage 错误信息
     * @return
     */
    public static ResponseMessage error(int code,int errorCode,String errorMessage){
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setCode(code);
        responseMessage.setErrorCode(errorCode);
        responseMessage.setErrorMessage(errorMessage);
        return responseMessage;
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
