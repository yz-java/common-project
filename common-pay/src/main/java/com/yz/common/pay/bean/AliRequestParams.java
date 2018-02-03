package com.yz.common.pay.bean;


import com.yz.common.pay.util.AlipayUtil;

/**
 * Created by yangzhao on 17/8/8.
 */
public class AliRequestParams extends RequestParams {

    private String app_id = AlipayUtil.app_id;

    private String applicationAppId = AlipayUtil.application_app_id;

    private String charset = "utf-8";

    private String signType = "RSA2";

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public String getApplicationAppId() {
        return applicationAppId;
    }

    public void setApplicationAppId(String applicationAppId) {
        this.applicationAppId = applicationAppId;
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
}
