package com.yz.common.web.filter;

import com.alibaba.fastjson.JSON;
import com.yz.common.core.utils.HttpUtil;
import com.yz.common.core.utils.StringUtils;
import com.yz.common.security.aes.AES;
import com.yz.common.security.aes.AES_CBC;
import com.yz.common.web.IHttpServletRequestWrapper;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author yangzhao
 * @Description 请求参数转换；数据保存Parameter中
 * @Date create by 14:45 18/2/17
 */
public class DataSafeFilter extends BaseFilter {

    private final AES aes = new AES_CBC();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        boolean processor = true;
        HttpServletRequest httpServletRequest = (HttpServletRequest)request;
        String url = httpServletRequest.getRequestURL().toString();
        IHttpServletRequestWrapper iHttpServletRequestWrapper = new IHttpServletRequestWrapper((HttpServletRequest) request,null);
        //解析请求数据
        try {
            byte[] data = HttpUtil.getRequestByteArray(httpServletRequest);
            if (data == null || data.length <= 0) {
                logger.info("请求数据为空,url=" + url);
                chain.doFilter(iHttpServletRequestWrapper,response);
                return;
            }
            String strData = new String(data, "UTF-8");
            logger.info("解密前数据：" + strData);
            if (StringUtils.isEmpty(strData)){
                processor = false;
            }
            strData = aes.decrypt(strData);
            logger.info("请求数据data:" + strData);
            if (StringUtils.isEmpty(strData)){
                processor = false;
            }
            Map map = JSON.parseObject(strData, HashMap.class);
            //将请求数据保存
            iHttpServletRequestWrapper.setAttribute("data",map);
            Set set = map.keySet();
            Iterator iterator = set.iterator();
            while (iterator.hasNext()){
                Object next = iterator.next();
                //将json数据以参数形式保存
                iHttpServletRequestWrapper.addParameter(next.toString(),map.get(next.toString()));
            }

        } catch (Exception e) {
            logger.error("请求失败！", e);
            processor = false;
        }
        if (processor){
            chain.doFilter(iHttpServletRequestWrapper,response);
        }
    }

    @Override
    public void destroy() {

    }
}
