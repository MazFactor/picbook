package com.jinghuan.common.enume;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jinghuan.common.constant.BaseMsgConstant;
import com.jinghuan.common.exception.ApplicationException;
import com.jinghuan.common.serializer.BaseEnumSerializer;

import java.text.MessageFormat;

/**
 * 产品类型枚举类
 */
@JsonSerialize(using = BaseEnumSerializer.class)
public enum ProductTypeEnum {

    FINISHED_PRODUCT(0, "产成品");

    private int id;
    private String code;

    @JsonCreator
    public static ProductTypeEnum getItem(Integer id) {
        for (ProductTypeEnum item : values()) {
            if (item.getId()==id) {
                return item;
            }
        }
        throw new ApplicationException(MessageFormat.format(BaseMsgConstant.BASE_MSG_ERROR_FORMAT_UNRECOGNIZED, BaseNodeTypeEnum.class, id));
    }

    ProductTypeEnum(int id, String code) {
        this.id = id;
        this.code = code;
    }

    public int getId() {
        return id;
    }

    public String getCode() {
        return code;
    }
}
