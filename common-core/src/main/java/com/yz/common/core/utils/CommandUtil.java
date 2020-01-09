package com.yz.common.core.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: yangzhao
 * @Date: 2020-01-09 12:55
 * @Description:
 */
public class CommandUtil {

    private static final Logger logger = LoggerFactory.getLogger(CommandUtil.class);

    public static String exec(String[] cmd) throws Exception {
        Process exec = Runtime.getRuntime().exec(cmd);
        exec.waitFor();
        InputStream inputStream = exec.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        String readLine;
        StringBuilder resultBuilder = new StringBuilder();
        while ((readLine = br.readLine()) != null) {
            resultBuilder.append(readLine);
        }
        logger.info("command result is " + resultBuilder.toString());
        return resultBuilder.toString();
    }
}
