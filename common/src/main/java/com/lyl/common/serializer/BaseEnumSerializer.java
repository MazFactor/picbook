package com.jinghuan.common.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.jinghuan.common.exception.ApplicationException;
import com.jinghuan.common.enume.BaseEnum;


import java.io.IOException;
import java.lang.reflect.Method;

/**
 * 枚举序列化
 *
 * @author WRQ
 * @date 2019/6/25
 * @since 1.0.0
 */
public class BaseEnumSerializer extends JsonSerializer<BaseEnum> {

    @Override
    public void serialize(BaseEnum enumObj, JsonGenerator jg, SerializerProvider provider) throws IOException {
        try {
            jg.writeStartObject();
            Class clazz = enumObj.getClass();
            Method[] methods = clazz.getDeclaredMethods();
            Method name = clazz.getMethod("name");
            jg.writeObjectField("id", enumObj.getId());
            for (Method m : methods) {
                String methodName = m.getName();
                Integer argCount = m.getParameterTypes().length;
                if (methodName.startsWith("get") && methodName.indexOf("getId") < 0 && argCount == 0) {
                    String fieldName = methodName.substring(3).toLowerCase();
                    jg.writeStringField(fieldName, m.invoke(enumObj).toString());
                }
            }
            jg.writeStringField("enumName", name.invoke(enumObj).toString());
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        } finally {
            jg.writeEndObject();
        }
    }
}
