package com.yz.common.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author yangzhao
 * @Description 读取properties配置文件工具类
 * @Date create by 13:25 18/3/4
 */
public class PropertyUtil {

    private static final Logger logger = LoggerFactory.getLogger(PropertyUtil.class);

    private Properties properties;

    private String resourceUri;

    public PropertyUtil(String resourceUri) {
        this.resourceUri = resourceUri;
        loadProperties(resourceUri);
    }


    private void loadProperties(String resourceUri) {
        logger.info("开始加载properties文件内容.......");
        properties = new Properties();
        InputStream in = null;
        try {
            //第一种，通过类加载器进行获取properties文件流
            in = PropertyUtil.class.getClassLoader().getResourceAsStream(resourceUri);
            //第二种，通过类进行获取properties文件流
            //in = PropertyUtil.class.getResourceAsStream("/jdbc.properties");
            properties.load(in);
        } catch (FileNotFoundException e) {
            logger.error("properties文件未找到");
        } catch (IOException e) {
            logger.error("出现IOException");
        } finally {
            try {
                if (null != in) {
                    in.close();
                }
            } catch (IOException e) {
                logger.error("properties文件流关闭出现异常");
            }
        }
        logger.info("加载properties文件内容完成...........");
    }

    public String getProperty(String key){
        return properties.getProperty(key);
    }

    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
}
