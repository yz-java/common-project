package com.yz.common.core.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * jackson
 *
 * @auther yangzhao
 * create by 17/10/9
 */
public class Jackson implements IJsonInterface {

    private static final Logger logger= LoggerFactory.getLogger(Jackson.class);

    @Override
    public <T> T parseObject(String json, Class<T> t) {
        try {
            return objectMapper.readValue(json, t);
        } catch (Exception e) {
            logger.error("JSON字符串转对象失败 ----", e);
        }
        return null;
    }

    @Override
    public <T> List<T> parseList(String json, Class<T> t) {
        try {
            Collection collection = this.parse(json, ArrayList.class, t);
            return (List<T>) collection;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toJsonString(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    private ObjectMapper objectMapper = new ObjectMapper();

    public Jackson() {
        //不允许出现特殊字符和转义符
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true) ;
        //支持单引号
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        // 设置输出时包含属性的风格
        objectMapper.configure(MapperFeature.USE_ANNOTATIONS, false);

        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
        //jackson 实体转json为NULL或者为空不参加序列化
		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        //jackson实体转json为null时修改null值为""
        objectMapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
            @Override
            public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
                jsonGenerator.writeString("");
            }
        });
    }

    /**
     * 获取泛型的Collection Type
     * @param collectionClass
     * @param elementClasses
     * @return
     */
    @SuppressWarnings("deprecation")
    public  JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return objectMapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }

    /**
     * 泛型反序列化 ,如List<T>,Map<K,V>
     * @param json
     * @param collectionClass
     * @param clazz
     * @return
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    public  Collection parse(String json, Class<?> collectionClass, Class<?> clazz) throws IOException {
        JavaType javaType = getCollectionType(collectionClass, clazz);
        return objectMapper.readValue(json, javaType);
    }
}
