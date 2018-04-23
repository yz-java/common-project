package com.yz.common.web.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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

}
