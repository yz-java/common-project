package com.yz.common.payment;


import com.yz.common.payment.enums.PayChannelEnum;

/**
 * @author: yangzhao
 * @Date: 2019/7/15 15:47
 * @Description:
 */
public class ThirdPayFactory {

    private static WXPayServiceImpl wxPayService;

    private static AliPayServiceImpl aliPayService;

    public void setWxPayServiceImpl(WXPayServiceImpl wxPayServiceImpl) {
        ThirdPayFactory.wxPayService = wxPayServiceImpl;
    }
    public void setAliPayService(AliPayServiceImpl aliPayServiceImpl) {
        ThirdPayFactory.aliPayService = aliPayService;
    }

    public static PayService  getPayService(int payChannel){
        if (payChannel== PayChannelEnum.WX_PAY.getCode()){
            return wxPayService;
        }else if (payChannel==PayChannelEnum.ALI_PAY.getCode()){
            return aliPayService;
        }
        return null;
    }

}
