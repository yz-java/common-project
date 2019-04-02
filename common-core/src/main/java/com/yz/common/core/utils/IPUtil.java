package com.yz.common.core.utils;

import com.yz.common.core.json.JSON;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.*;

/**
 * IP工具类
 * Created by yangzhao on 16/11/16.
 */
public class IPUtil {

    private static final String SINA_API="message://int.dpool.sina.com.cn/iplookup/iplookup.php?format=json&ip=";
    
    private static final String TAOBAO_API="message://ip.taobao.com/service/getIpInfo.php?ip=";

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

    /**
     * 获取本机IP地址
     * @return
     * @throws Exception
     */
    public static InetAddress getLocalHostLANAddress() {
        try {
            InetAddress candidateAddress = null;
            // 遍历所有的网络接口
            for (Enumeration ifaces = NetworkInterface.getNetworkInterfaces(); ifaces.hasMoreElements(); ) {
                NetworkInterface iface = (NetworkInterface) ifaces.nextElement();
                // 在所有的接口下再遍历IP
                for (Enumeration inetAddrs = iface.getInetAddresses(); inetAddrs.hasMoreElements(); ) {
                    InetAddress inetAddr = (InetAddress) inetAddrs.nextElement();
                    if (!inetAddr.isLoopbackAddress()) {// 排除loopback类型地址
                        if (inetAddr.isSiteLocalAddress()) {
                            // 如果是site-local地址，就是它了
                            return inetAddr;
                        } else if (candidateAddress == null) {
                            // site-local类型的地址未被发现，先记录候选地址
                            candidateAddress = inetAddr;
                        }
                    }
                }
            }
            if (candidateAddress != null) {
                return candidateAddress;
            }
            // 如果没有发现 non-loopback地址.只能用最次选的方案
            InetAddress jdkSuppliedAddress = InetAddress.getLocalHost();
            return jdkSuppliedAddress;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
