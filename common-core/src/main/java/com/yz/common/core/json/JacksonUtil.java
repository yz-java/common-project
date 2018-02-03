package com.yz.common.core.json;

import java.io.IOException;
import java.util.Collection;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.fasterxml.jackson.core.JsonParser.Feature;

/**
 * json工具类
 * @author yangzhao 2015年10月28日
 */
public class JacksonUtil {

	private static final Logger logger=LogManager.getLogger(JacksonUtil.class);

	private static ObjectMapper objectMapper = new ObjectMapper();

	static {
		//不允许出现特殊字符和转义符
		objectMapper.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true) ;
		//支持单引号
		objectMapper.configure(Feature.ALLOW_SINGLE_QUOTES, true);
		// 设置输出时包含属性的风格
		objectMapper.configure(MapperFeature.USE_ANNOTATIONS, false);
		
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
		//jackson 实体转json为NULL或者为空不参加序列化
//		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

		//jackson实体转json为null时修改null值为""
		objectMapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
			@Override
			public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
				jsonGenerator.writeString("");
			}
		});
	}

	/**
	 * JSON字符串转对象
	 * @param json
	 * @param valueType
	 * @return
	 */
	public static <T> T parse(String json, Class<T> valueType) {

		try {
			return objectMapper.readValue(json, valueType);
		} catch (Exception e) {
			logger.error("JSON字符串转对象失败 ----", e);
		}
		return null;
	}

	/**
	 * 对象转json字符串
	 * @param obj
	 * @return
	 */
	public static String parse(Object obj) {
		try {
			return objectMapper.writeValueAsString(obj);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 获取泛型的Collection Type
	 * @param collectionClass
	 * @param elementClasses
     * @return
     */
	@SuppressWarnings("deprecation")
	public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
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
	public static Collection parse(String json, Class<?> collectionClass, Class<?> clazz) {
		JavaType javaType = getCollectionType(collectionClass, clazz);
		try {
			return objectMapper.readValue(json, javaType);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
