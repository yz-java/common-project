package com.yz.common.pay.util;

import com.yz.common.core.utils.MD5Util;
import org.apache.http.NameValuePair;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public final class WXPayUtil {

	public static String KEY="";

	public static String APPID="";

	public static String MCHID="";
    /**
     * 微信回调接口
     */
	public static final String pay_notify_url = "";

	public static  String getSign(Map<String,String> map){
        ArrayList<String> list = new ArrayList<String>();
        for(Map.Entry<String,String> entry:map.entrySet()){
            if(entry.getValue()!=""&& !entry.getKey().equalsIgnoreCase("sign")){
                list.add(entry.getKey() + "=" + entry.getValue() + "&");
            }
        }
        int size = list.size();
        String [] arrayToSort = list.toArray(new String[size]);
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < size; i ++) {
            sb.append(arrayToSort[i]);
        }
        String result = sb.toString();
        result += "key=" +KEY;
        result = MD5Util.MD5Encode(result, "UTF-8").toUpperCase();
        return result;
    }

	public static String getSign(List<NameValuePair> params) {
		ArrayList<String> list = new ArrayList<String>();
		StringBuilder sb = new StringBuilder();
		for(NameValuePair pair:params){
		    if (pair.getValue()!=""&& !pair.getValue().equalsIgnoreCase("sign"))
			list.add(pair.getName() + "=" + pair.getValue() + "&");
		}
		int size = list.size();
        String [] arrayToSort = list.toArray(new String[size]);
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
        for(int i = 0; i < size; i ++) {
            sb.append(arrayToSort[i]);
        }
        String result = sb.toString();
        result += "key=" +KEY;
        byte[] data=null;
		try {
			data = result.getBytes("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
        String appSign = MD5Util.sign(data).toUpperCase();
		return appSign;
	}
}
