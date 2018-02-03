package com.yz.common.core.utils;


import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * SolrAPI工具类
 * Created by yangzhao on 17/7/27.
 */
public class SolrAPI {

    private static Logger logger = LoggerFactory.getLogger(SolrAPI.class);

    private static HttpSolrClient httpSolrClient;

    private static String serverUrl;

    private static final Lock lock = new ReentrantLock();

    private static HttpSolrClient getHttpSolrClient(){
        if (serverUrl==null){
            throw new RuntimeException();
        }
        return getHttpSolrClient(serverUrl);
    }

    public static HttpSolrClient getHttpSolrClient(String serverUrl) {
        lock.lock();
        if (httpSolrClient == null) {
            httpSolrClient = new HttpSolrClient(serverUrl);
        }
        lock.unlock();
        return httpSolrClient;
    }

    public static QueryResponse query(SolrQuery solrQuery) throws Exception {
        QueryResponse response = getHttpSolrClient().query(solrQuery);
        return response;
    }

    /**
     * response解析对象
     * @param queryResponse
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> T parseObject(QueryResponse queryResponse, Class<T> tClass) {
        SolrDocumentList results = queryResponse.getResults();
        SolrDocument document = results.get(0);
        Field[] fields = tClass.getDeclaredFields();
        T instance = null;
        try {
            instance = tClass.newInstance();
            for (Field field : fields) {
                String fieldName = field.getName();
                Object value = document.get(fieldName);
                String methodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                Method declaredMethod = tClass.getDeclaredMethod(methodName, field.getType());
                value = ClassUtil.parse(field.getType(), value);
                declaredMethod.invoke(instance, value);
            }
        } catch (Exception e) {
            logger.error("SolrAPI数据解析出错啦！------ " + e);
        }

        return instance;
    }

    /**
     * response解析数组模型
     * @param queryResponse
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> List<T> parseList(QueryResponse queryResponse, Class<T> tClass) {
        SolrDocumentList results = queryResponse.getResults();
        List<T> list = new ArrayList<T>();
        for (SolrDocument document : results) {
            Field[] fields = tClass.getDeclaredFields();
            try {
                T instance = tClass.newInstance();
                for (Field field : fields) {
                    String fieldName = field.getName();
                    Object value = document.get(fieldName);
                    if (value == null) {
                        continue;
                    }
                    String methodName = ClassUtil.createFieldMethodName("set", fieldName);
                    Method declaredMethod = tClass.getDeclaredMethod(methodName, field.getType());
                    value = ClassUtil.parse(field.getType(), value);
                    declaredMethod.invoke(instance, value);
                }
                list.add(instance);
            } catch (Exception e) {
                logger.error("SolrAPI数据解析出错啦！------ " + e);
                return null;
            }

        }
        return list;
    }

}
