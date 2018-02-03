package com.yz.common.pay.service;

public class PayFactory {
	
	public IPay init(int payWay) {
		IPay payInterface = null;
		switch (payWay) {
		case 1:
			payInterface = new WXPay();
			break;
		case 2:
			payInterface = new AliPay();
			break;
		}
		return payInterface;
	}

}
