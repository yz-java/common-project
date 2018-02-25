package com.yz.common.web;

import com.alibaba.fastjson.JSONArray;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.*;

/**
 * @author yangzhao
 * @Description
 * @Date create by 00:02 18/2/15
 */
public class IHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private Map<String , String[]> params = new HashMap<String, String[]>();

    public IHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
        //将参数表，赋予给当前的Map以便于持有request中的参数
        this.params.putAll(request.getParameterMap());
    }
    //重载一个构造方法
    public IHttpServletRequestWrapper(HttpServletRequest request , Map<String , String[]> extendParams) {
        this(request);
        addAllParameters(extendParams);//这里将扩展参数写入参数表
    }

    @Override
    public String getParameter(String name) {//重写getParameter，代表参数从当前类中的map获取
        String[]values = params.get(name);
        if(values == null || values.length == 0) {
            return null;
        }
        return values[0];
    }

    @Override
    public String[] getParameterValues(String name) {//同上
        return params.get(name);
    }

    public void addAllParameters(Map<String , String[]>otherParams) {//增加多个参数
        if (otherParams==null){
            return;
        }
        for(Map.Entry<String , String[]>entry : otherParams.entrySet()) {
            addParameter(entry.getKey() , entry.getValue());
        }
    }

    public void addParameter(String name , Object value) {//增加参数
        if(value == null) {
            return;
        }
        if(value instanceof String[]) {
            params.put(name , (String[])value);
        }else if(value instanceof String) {
            params.put(name , new String[] {(String)value});
        }else if (value instanceof JSONArray){
            JSONArray jsonArray = (JSONArray) value;
            Object[] objects = jsonArray.toArray();
            String[] values = new String[jsonArray.size()];
            for (int i=0;i<values.length;i++){
                values[i]=objects[i].toString();
            }
            params.put(name,values);
        }else {
            params.put(name , new String[] {String.valueOf(value)});
        }
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return params;
    }

    @Override
    public Enumeration<String> getParameterNames() {
        if(this.getRequest() == null) {
            throw new IllegalStateException("requestFacade.nullRequest");
        } else {
            return Collections.enumeration(params.keySet());
        }
    }

    public void emptyParameters(){
        params.clear();
    }
}
