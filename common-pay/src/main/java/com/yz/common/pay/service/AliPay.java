package com.yz.common.pay.service;

import com.yz.common.core.utils.DateUtils;
import com.yz.common.core.utils.HttpUtil;
import com.yz.common.core.utils.StringUtils;
import com.yz.common.pay.bean.AliPayParams;
import com.yz.common.pay.bean.AliRefundParams;
import com.yz.common.pay.util.AlipayUtil;
import com.yz.common.core.json.JSON;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;


public class AliPay implements IPay {

	@Override
	public <T> String createOrder(T t) {
		return "";
	}

	@Override
	public <T> Object refund(T t) {
		AliRefundParams aliRefundParams = (AliRefundParams) t;
		Map<String, String> params=new HashMap<String, String>();
		params.put("app_id", aliRefundParams.getApplicationAppId());
		params.put("method",aliRefundParams.getMethod());
		params.put("sign_type",aliRefundParams.getSignType());
		String timestamp = DateUtils.FormatFullDate(new Date());
		params.put("timestamp",timestamp);
		params.put("charset",aliRefundParams.getCharset());
		params.put("version", aliRefundParams.getVersion());
		Map biz_content = new HashMap();
		biz_content.put("out_trade_no",aliRefundParams.getOutTradeNo());
		biz_content.put("refund_amount",aliRefundParams.getRefundAmount());
		params.put("biz_content", JSON.getInterface().toJsonString(biz_content));
		Map<String, String> paraFilter = AlipayUtil.paraFilter(params);
		String createLinkString = AlipayUtil.createLinkString(paraFilter);
		String sign= AlipayUtil.signRSA256(createLinkString, AlipayUtil.application_privateKey,"UTF-8");
		params.put("sign",sign);
		StringBuilder payParams = new StringBuilder();
		params.forEach((k,v)->{
			try {
				payParams.append(k+"="+URLEncoder.encode(v,"UTF-8")+"&");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		});
		String resData = HttpUtil.getRequest(AlipayUtil.refund_url+payParams.toString());
		if (StringUtils.isEmpty(resData)) {
			return null;
		}
		HashMap parse = JSON.getInterface().parseObject(resData,HashMap.class);
		return parse;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public <T> Object createPayParams(T t) {
		AliPayParams aliPayParams = (AliPayParams) t;
		Map<String,String> map=new LinkedHashMap<String,String>();
		map.put("app_id", aliPayParams.getApplicationAppId());
		map.put("method", aliPayParams.getMethod());
		map.put("charset", aliPayParams.getCharset());
		map.put("version", aliPayParams.getVersion());
		map.put("notify_url",aliPayParams.getNotifyUrl());
		map.put("timestamp", DateUtils.FormatDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
		Map biz_content = new HashMap();
		biz_content.put("timeout_express",aliPayParams.getTimeoutExpress());
		biz_content.put("product_code",aliPayParams.getProductCode());
		biz_content.put("total_amount",String.valueOf(aliPayParams.getTotalAmount()));
		biz_content.put("subject",aliPayParams.getSubject());
		biz_content.put("body",aliPayParams.getBody());
		biz_content.put("out_trade_no",aliPayParams.getOutTradeNo());
		map.put("biz_content", JSON.getInterface().toJsonString(biz_content));
		map.put("sign_type", AlipayUtil.sign_type);
		Map<String, String> paraFilter = AlipayUtil.paraFilter(map);
		String createLinkString = AlipayUtil.createLinkString(paraFilter);
		String sign = AlipayUtil.signRSA256(createLinkString, AlipayUtil.application_privateKey, "utf-8");
		map.put("sign", sign);
		StringBuilder payParams = new StringBuilder();
		map.forEach((k,v)->{
			try {
				payParams.append(k+"="+URLEncoder.encode(v,"UTF-8")+"&");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		});
		return payParams.toString();
	}

	@Override
	public Object queryOrderStatus(String orderId) {
		Map map = new HashMap();
		map.put("app_id", AlipayUtil.application_app_id);
		map.put("method","alipay.trade.query");
		map.put("charset","utf-8");
		map.put("sign_type", AlipayUtil.sign_type);
		map.put("timestamp", DateUtils.FormatFullDate(new Date()));
		map.put("version","1.0");
		map.put("biz_content", "{\"out_trade_no\":\""+orderId+"\"}");
		Map paraFilter = AlipayUtil.paraFilter(map);
		String linkString = AlipayUtil.createLinkString(paraFilter);
		String sign = AlipayUtil.signRSA256(linkString, AlipayUtil.application_privateKey, "utf-8");
		map.put("sign",sign);
		Set set = map.keySet();
		Iterator iterator = set.iterator();
		StringBuilder stringBuilder = new StringBuilder();
		while (iterator.hasNext()){
			String key = iterator.next().toString();
			String value = map.get(key).toString();
			stringBuilder.append(key+"="+URLEncoder.encode(value)+"&");

		}
		String params = stringBuilder.toString();
		params = params.substring(0,params.length()-1);
		String request = HttpUtil.getRequest(AlipayUtil.order_query_url + params);
		HashMap parse = JSON.getInterface().parseObject(request, HashMap.class);
//		Map alipay_trade_query_response = (Map) parse.get("alipay_trade_query_response");
//		String msg = alipay_trade_query_response.get("msg").toString();
//		if (msg.equals("Success")){
//			if (map.get("trade_status").toString().equals("TRADE_SUCCESS")) {
//				return OrderStatus.SUCCESS.getStatus();
//			}
//		}
		return parse;
	}
}
