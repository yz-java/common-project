package com.yz.common.web.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author yangzhao
 * @Description
 * @Date create by 16:39 18/2/17
 */
public abstract class BaseFilter implements Filter {
    public final Logger logger = LoggerFactory.getLogger(BaseFilter.class);

    public void serverReturn(HttpServletResponse response, String errorInfo){
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            writer.write(errorInfo);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            writer.close();
        }

    }
}
