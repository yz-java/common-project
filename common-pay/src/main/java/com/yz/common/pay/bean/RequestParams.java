package com.yz.common.pay.bean;

import com.yz.common.core.utils.MD5Util;

import java.util.Random;

/**
 * Created by yangzhao on 17/8/8.
 */
public abstract class RequestParams {

    public String genNonceStr() {
        Random random = new Random();
        return MD5Util.md5(String.valueOf(random.nextInt(10000)));
    }
}
