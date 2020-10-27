package com.jinghuan.common.enume;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import com.jinghuan.common.constant.BaseMsgConstant;
import com.jinghuan.common.exception.ApplicationException;
import com.jinghuan.common.serializer.BaseEnumSerializer;

import java.text.MessageFormat;

/**
 * 树结点类型枚举类
 *
 * @author WRQ
 * @date 2019/6/26
 * @since 1.0.0
 */
@JsonSerialize(using = BaseEnumSerializer.class)
public enum BaseNodeTypeEnum implements BaseEnum<Integer> {

    ROOT(0, "根结点"),
    MIDDLE(1, "中间结点"),
    LEAF(2, "叶子结点");

    private Integer id;
    private String name;

    BaseNodeTypeEnum(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @JsonCreator
    public static BaseNodeTypeEnum getItem(Integer id) {
        for (BaseNodeTypeEnum item : values()) {
            if (item.getId().equals(id)) {
                return item;
            }
        }
        throw new ApplicationException(MessageFormat.format(BaseMsgConstant.BASE_MSG_ERROR_FORMAT_UNRECOGNIZED, BaseNodeTypeEnum.class, id));
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "BaseNodeTypeEnum{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
