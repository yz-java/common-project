package com.yz.common.web.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yangzhao
 * @Description
 * @Date create by 17:05 18/4/23
 */
public class WebUtil {

    private static final Logger logger = LoggerFactory.getLogger(WebUtil.class);

    /**
     * webserver返回信息
     * @param response
     * @param info
     */
    public static void serverReturn(HttpServletResponse response, String info){
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            writer.write(info);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            writer.close();
        }

    }

    /**
     * 将http form 请求参数转换为map
     * @param request
     * @return
     */
    public static Map<String, String> parseFromToMap(HttpServletRequest request) {

        Map<String, String> parameters = new HashMap<>();

        Enumeration<String> parameterNames = request.getParameterNames();

        while(parameterNames.hasMoreElements()) {

            String parameterName = parameterNames.nextElement();

            parameters.put(parameterName, request.getParameter(parameterName));
        }

        return parameters;
    }

    /**
     * 获取请求IP地址
     * @param request
     * @return
     */
    public static String getIPAddress(HttpServletRequest request) {
        String ip = null;

        //X-Forwarded-For：Squid 服务代理
        String ipAddresses = request.getHeader("X-Forwarded-For");

        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            //Proxy-Client-IP：apache 服务代理
            ipAddresses = request.getHeader("Proxy-Client-IP");
        }

        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            //WL-Proxy-Client-IP：weblogic 服务代理
            ipAddresses = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            //HTTP_CLIENT_IP：有些代理服务器
            ipAddresses = request.getHeader("HTTP_CLIENT_IP");
        }

        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            //X-Real-IP：nginx服务代理
            ipAddresses = request.getHeader("X-Real-IP");
        }

        //有些网络通过多层代理，那么获取到的ip就会有多个，一般都是通过逗号（,）分割开来，并且第一个ip为客户端的真实IP
        if (ipAddresses != null && ipAddresses.length() != 0) {
            ip = ipAddresses.split(",")[0];
        }

        //还是不能获取到，最后再通过request.getRemoteAddr();获取
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * request对象转字节
     * @param request
     * @return
     */
    public static byte[] getRequestByteArray(HttpServletRequest request) {
        byte[] dataOrigin=null;
        InputStream is = null;
        try {
            int contentLength = request.getContentLength();
            if (contentLength <= 0) {
                return null;
            }
            dataOrigin= new byte[contentLength];
            is = request.getInputStream();
            is.read(dataOrigin);
        } catch (Exception e) {
            logger.error("request对象转字节失败！", e);
        }finally {
            try {
                if (is!=null){
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return dataOrigin;
    }

}
