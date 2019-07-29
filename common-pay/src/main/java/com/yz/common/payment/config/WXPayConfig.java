package com.yz.common.payment.config;

/**
 * 微信支付配置类
 */
public class WXPayConfig extends PayConfig{
    /**
     * 公众账号ID
     */
    private String appId;
    /**
     * 商户号
     */
    private String mchId;

    private String mchKey;

    public enum SignType {
        MD5, HMACSHA256
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getMchKey() {
        return mchKey;
    }

    public void setMchKey(String mchKey) {
        this.mchKey = mchKey;
    }
}

