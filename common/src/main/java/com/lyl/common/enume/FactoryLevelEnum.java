package com.jinghuan.common.enume;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jinghuan.common.constant.BaseMsgConstant;
import com.jinghuan.common.exception.ApplicationException;
import com.jinghuan.common.serializer.BaseEnumSerializer;

import java.text.MessageFormat;


/**
 * 工厂类型枚举
 *
 * @author MX
 * @date 2019/4/10
 * @since 1.0.0
 */
@JsonSerialize(using = BaseEnumSerializer.class)
public enum FactoryLevelEnum {

    FACTORY(0, "工厂"),
    WORKSHOP(1, "车间"),
    LINE(2, "线体"),
    WORKSTATION(3, "工位");

    private int id;
    private String code;

    @JsonCreator
    public static FactoryLevelEnum getItem(Integer id) {
        for (FactoryLevelEnum item : values()) {
            if (item.getId()==id) {
                return item;
            }
        }
        throw new ApplicationException(MessageFormat.format(BaseMsgConstant.BASE_MSG_ERROR_FORMAT_UNRECOGNIZED, BaseNodeTypeEnum.class, id));
    }

    FactoryLevelEnum(int id, String code) {
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
