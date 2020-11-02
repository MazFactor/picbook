package com.lyl.common.util;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
//import com.jinghuan.zlog.ZLSimpleLogger;
//import com.jinghuan.zlog.ZLoggerFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import java.io.IOException;
import java.io.Writer;

/**
 * 
 * @author wmb
 *
 */
public class JsonUtils {
//    private static final ZLSimpleLogger logger = ZLoggerFactory.getSimpleLogger(JsonUtils.class);

	/** ObjectMapper */
	private static ObjectMapper mapper = new ObjectMapper();
	static {
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}
	/**
	 * 不可实例化
	 */
	private JsonUtils() {
	}

	/**
	 * 将对象转换为JSON字符串
	 * 
	 * @param value
	 *            对象
	 * @return JSOn字符串
	 */
	public static String toJson(Object value) {
		try {
			return mapper.writeValueAsString(value);
		} catch (Exception e) {
			System.out.println(e.getMessage());
//			logger.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 将JSON字符串转换为对象
	 * 
	 * @param json
	 *            JSON字符串
	 * @param valueType
	 *            对象类型
	 * @return 对象
	 */
	public static <T> T toObject(String json, Class<T> valueType) {
		Assert.hasText(json);
		Assert.notNull(valueType);
		try {
			return mapper.readValue(json, valueType);
		} catch (Exception e) {
			throw new RuntimeException("参数不合法!");
		}
	}

	/**
	 * 将JSON字符串转换为对象
	 * 
	 * @param json
	 *            JSON字符串
	 * @param valueType
	 *            对象类型
	 * @return 对象
	 */
	public static Object toObject(String json, String valueType) {
		Assert.hasText(json);
		Assert.notNull(valueType);
		try {
			return mapper.readValue(json, Class.forName(valueType));
		} catch (Exception e) {
			System.out.println(e.getMessage());
//			logger.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 将JSON字符串转换为对象
	 * 
	 * @param json
	 *            JSON字符串
	 * @param typeReference
	 *            对象类型
	 * @return 对象
	 */
	public static <T> T toObject(String json, TypeReference<?> typeReference) {
		Assert.hasText(json);
		Assert.notNull(typeReference);
		try {
			return (T) mapper.readValue(json, typeReference);
		} catch (Exception e) {
			System.out.println(e.getMessage());
//			logger.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 将JSON字符串转换为对象
	 * 
	 * @param json
	 *            JSON字符串
	 * @param javaType
	 *            对象类型
	 * @return 对象
	 */
	public static <T> T toObject(String json, JavaType javaType) {
		Assert.hasText(json);
		Assert.notNull(javaType);
		try {
			return mapper.readValue(json, javaType);
		} catch (Exception e) {
			System.out.println(e.getMessage());
//			logger.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 将对象转换为JSON流
	 * 
	 * @param writer
	 *            writer
	 * @param value
	 *            对象
	 */
	public static void writeValue(Writer writer, Object value) {
		try {
			mapper.writeValue(writer, value);
		} catch (JsonGenerationException e) {
			System.out.println(e.getMessage());
//			logger.error(e.getMessage(), e);
		} catch (JsonMappingException e) {
			System.out.println(e.getMessage());
//			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			System.out.println(e.getMessage());
//			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 将json转换成JsonNode
	 * @param json
	 * @return
	 */
	public static JsonNode toJsonNode(String json) {
		try {
			if (StringUtils.isNotBlank(json)) {
				return mapper.readTree(json);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
//			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
		return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
	}

}
