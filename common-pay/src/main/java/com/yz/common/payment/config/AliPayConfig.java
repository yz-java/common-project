package com.yz.common.payment.config;

/**
 * @author: yangzhao
 * @Date: 2019/7/10 14:24
 * @Description:
 */
public class AliPayConfig  extends PayConfig {

    /**
     * 支付宝分配给开发者的应用ID
     */
    private String appId;
    /**
     * 请求使用的编码格式
     */
    private String charset="utf-8";
    /**
     * 商户生成签名字符串所使用的签名算法类型，目前支持RSA2和RSA，推荐使用RSA2
     */
    private String signType="RSA2";

    private String appPrivateKey;

    private String aliPayPublicKey;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getAppPrivateKey() {
        return appPrivateKey;
    }

    public void setAppPrivateKey(String appPrivateKey) {
        this.appPrivateKey = appPrivateKey;
    }

    public String getAliPayPublicKey() {
        return aliPayPublicKey;
    }

    public void setAliPayPublicKey(String aliPayPublicKey) {
        this.aliPayPublicKey = aliPayPublicKey;
    }
}
