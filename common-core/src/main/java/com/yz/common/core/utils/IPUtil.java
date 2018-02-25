package com.yz.common.core.utils;

import com.yz.common.core.json.JSON;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * IP工具类
 * Created by yangzhao on 16/11/16.
 */
public class IPUtil {

    private static final String SINA_API="http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=json&ip=";
    
    private static final String TAOBAO_API="http://ip.taobao.com/service/getIpInfo.php?ip=";

    /**
     * 免费IP查询接口
     * @param ip
     * @return
     */
    public static Map query(String ip){
        String request = HttpUtil.getRequest(SINA_API+ip);
        HashMap responseData = JSON.getInterface().parseObject(request, HashMap.class);
        Set set = responseData.keySet();
        Iterator iterator = set.iterator();
        while (iterator.hasNext()){
            String key = iterator.next().toString();
            responseData.put(key,new String(responseData.get(key).toString()));
        }
        return responseData;
    }

}
