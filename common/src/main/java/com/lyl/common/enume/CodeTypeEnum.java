package com.jinghuan.common.enume;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.jinghuan.common.constant.BaseMsgConstant;
import com.jinghuan.common.exception.ApplicationException;

import java.text.MessageFormat;

/**
 * @author dcc
 * @date 2019-11-14 14:05
 */
public enum CodeTypeEnum {

    FACTORY_CODE(0, "工厂code"),
    WORKSHOP_CODE(1, "车间code"),
    LINE_CODE(2, "线体code"),
    WORKSTATION_CODE(3, "工位code");

    private int id;
    private String code;

    @JsonCreator
    public static CodeTypeEnum getItem(Integer id) {
        for (CodeTypeEnum item : values()) {
            if (item.getId()==id) {
                return item;
            }
        }
        throw new ApplicationException(MessageFormat.format(BaseMsgConstant.BASE_MSG_ERROR_FORMAT_UNRECOGNIZED, BaseNodeTypeEnum.class, id));
    }

    CodeTypeEnum(int id, String code) {
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
