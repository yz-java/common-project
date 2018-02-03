package com.yz.common.pay.bean;

import com.yz.common.pay.util.WXPayUtil;

/**
 * Created by yangzhao on 17/8/8.
 */
public class WXRequestParams extends RequestParams {

    private String appId = WXPayUtil.APPID;

    private String mchId = WXPayUtil.MCHID;

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
}
