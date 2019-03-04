package com.yz.common.pay.service;

import com.yz.common.core.utils.*;
import com.yz.common.core.json.JSON;
import com.yz.common.pay.bean.WXCreateOrderParams;
import com.yz.common.pay.bean.WXPayParams;
import com.yz.common.pay.bean.WXRefundParams;
import com.yz.common.pay.util.WXPayUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import java.util.*;

public class WXPay implements IPay {

	@Override
	public <T> Map<String, Object> createOrder(T order) {
		WXCreateOrderParams wxCreateOrderParams = (WXCreateOrderParams) order;
		List<NameValuePair> params = new LinkedList<NameValuePair>();
		params.add(new BasicNameValuePair("appid",wxCreateOrderParams.getAppId()));
		params.add(new BasicNameValuePair("mch_id", wxCreateOrderParams.getMchId()));
		params.add(new BasicNameValuePair("nonce_str", genNonceStr()));
		params.add(new BasicNameValuePair("body", wxCreateOrderParams.getBody()));

		params.add(new BasicNameValuePair("attach", JSON.getInterface().toJsonString(wxCreateOrderParams.getAttach())));
		params.add(new BasicNameValuePair("out_trade_no", wxCreateOrderParams.getOutTradeNo()));
		params.add(new BasicNameValuePair("total_fee", String.valueOf((int)(wxCreateOrderParams.getTotalFee()*100))));
		params.add(new BasicNameValuePair("spbill_create_ip", wxCreateOrderParams.getSpbillCreateIp()));
		String timeExpire= DateUtils.FormatDate(DateUtils.getDate(new Date(System.currentTimeMillis()),+30), "yyyyMMddHHmmss");
		logger.debug("微信生成预支付订单交易结束时间："+timeExpire);
		params.add(new BasicNameValuePair("time_expire",timeExpire));//订单失效时间
		params.add(new BasicNameValuePair("notify_url", wxCreateOrderParams.getNotifyUrl()));//异步通知
		params.add(new BasicNameValuePair("trade_type", wxCreateOrderParams.getTradeType()));
		String sign = WXPayUtil.getSign(params);//签名
		params.add(new BasicNameValuePair("sign", sign));
		String reqData = XMLUtil.toXml(params);
		String resData = HttpUtil.sendPost("https://api.mch.weixin.qq.com/pay/unifiedorder", reqData);
		Map<String, Object> map = XMLUtil.getMapFromXML(resData);
		logger.debug("微信生成预支付订单返回数据："+map.toString());
		String returnCode = map.get("return_code").toString();
		if (returnCode.equalsIgnoreCase("FAIL")) {
			return null;
		}
		String resultCode = map.get("result_code").toString();
		if (resultCode.equalsIgnoreCase("SUCCESS")) {
			//预支付订单生成成功，返回prepay_id
//			String prepayId = map.get("prepay_id").toString();
			return map;
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public <T> Object refund(T t) throws Exception {
		WXRefundParams wxRefundParams = (WXRefundParams) t;
		String path = this.getClass().getResource("/apiclient_cert.p12").getPath();
		HttpClient client= HttpUtil.createSSL(path, WXPayUtil.MCHID);
		Map<String,String> params=new HashMap<>();
		params.put("appid", wxRefundParams.getAppId());
		params.put("mch_id", wxRefundParams.getMchId());
		params.put("nonce_str", wxRefundParams.genNonceStr());
		params.put("out_trade_no", wxRefundParams.getOutTradeNo());
		params.put("out_refund_no",wxRefundParams.getOutRefundNo());
		params.put("total_fee", String.valueOf((int)(wxRefundParams.getTotalFee()*100)));
		params.put("refund_fee",String.valueOf((int)(wxRefundParams.getRefundFee()*100)));
		params.put("op_user_id","1446023502");
		String sign = WXPayUtil.getSign(params);
		params.put("sign", sign);
		String xmlstring = XMLUtil.toXml(params);
		HttpPost post=new HttpPost("https://api.mch.weixin.qq.com/secapi/pay/refund");
		StringEntity postEntity = new StringEntity(xmlstring,"utf-8");
		post.setEntity(postEntity);
		String result=null;
		HttpResponse response = client.execute(post);
		HttpEntity entity = response.getEntity();
		result = EntityUtils.toString(entity, "UTF-8");
		Map map= XMLUtil.getMapFromXML(result);
		return map;
	}

	private String genNonceStr() {
		Random random = new Random();
		return MD5Util.md5(String.valueOf(random.nextInt(10000)));
	}
	/**
	 * 查询订单状态
	 * @param orderId
	 * @return
	 */
	@Override
	public Object queryOrderStatus(String orderId){
		List<NameValuePair> params = new LinkedList<NameValuePair>();
		params.add(new BasicNameValuePair("appid", WXPayUtil.APPID));
		params.add(new BasicNameValuePair("mch_id", WXPayUtil.MCHID));
		params.add(new BasicNameValuePair("out_trade_no",orderId) );
		params.add(new BasicNameValuePair("nonce_str", genNonceStr()));
		String sign = WXPayUtil.getSign(params);//签名
		params.add(new BasicNameValuePair("sign", sign));
		String reqData = XMLUtil.toXml(params);
		String resData = HttpUtil.sendPost("https://api.mch.weixin.qq.com/pay/orderquery", reqData);
		Map<String, Object> mapFromXML = XMLUtil.getMapFromXML(resData);
		return mapFromXML;
	}

	/**
	 * 退款状态查询
	 * @param
	 * @return
	 */
	public String refundQuery(long orderId){
		Map<String,String> map=new HashMap<>();
		map.put("appid", WXPayUtil.APPID);
		map.put("mch_id", WXPayUtil.MCHID);
		map.put("nonce_str", genNonceStr());
		map.put("out_trade_no",String.valueOf(orderId) );
		String sign = WXPayUtil.getSign(map);
		map.put("sign", sign);
		String xmlstring = XMLUtil.toXml(map);
		String result= HttpUtil.sendPost("https://api.mch.weixin.qq.com/pay/refundquery", xmlstring);
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public <T> Map<String, String> createPayParams(T t) {
		WXPayParams wxPayParams = (WXPayParams) t;
		Map<String,String> map=new LinkedHashMap<String,String>();
		map.put("appid", wxPayParams.getAppId());
		map.put("partnerid", wxPayParams.getMchId());
		map.put("prepayid", wxPayParams.getPrepayId());
		map.put("package", wxPayParams.getPkg());
		map.put("noncestr", UUIDUtil.getUUID());
		Date date = new Date(1970);
		long s = date.getTime();
		long e=System.currentTimeMillis();
		map.put("timestamp", String.valueOf((e-s)/1000));
		String sign = WXPayUtil.getSign(map);
		map.put("sign", sign);
		return map;
	}
}
