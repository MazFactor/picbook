package com.lyl.common.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.IntNode;

import com.lyl.common.enume.BaseEnum;
import com.lyl.common.util.EnumUtil;
import org.springframework.beans.BeanUtils;

import java.io.IOException;

/**
 * 枚举反序列化
 *
 * @author WRQ
 * @date 2019/6/25
 * @since 1.0.0
 */
public class BaseEnumDeserializer extends JsonDeserializer<BaseEnum> {

    @Override
    public BaseEnum deserialize(JsonParser jp, DeserializationContext dc) throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);
        String currentName = jp.currentName();
        Object currentValue = jp.getCurrentValue();
        Class findPropertyType = BeanUtils.findPropertyType(currentName, currentValue.getClass());
        BaseEnum valueOf;
        JsonNode idNode = node.get("id");
        if (idNode instanceof IntNode) {
            valueOf = EnumUtil.indexOf(findPropertyType, idNode.intValue());
        } else {
            valueOf = EnumUtil.indexOf(findPropertyType, idNode.asText());
        }
        return valueOf;
    }
}