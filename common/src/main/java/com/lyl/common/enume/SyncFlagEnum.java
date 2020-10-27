package com.jinghuan.common.enume;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.jinghuan.common.constant.BaseMsgConstant;
import com.jinghuan.common.exception.ApplicationException;

import java.text.MessageFormat;

/**
 * @author dcc
 * 是否自动同步数据（0手动插入，1自动同步，默认1）
 * @date 2020-01-13 16:05
 */
public enum SyncFlagEnum {
    MANUAL(0, "手动插入"),
    AUTO_MATIC(1, "自动同步");

    private int id;
    private String code;

    @JsonCreator
    public static SyncFlagEnum getItem(Integer id) {
        for (SyncFlagEnum item : values()) {
            if (item.getId()==id) {
                return item;
            }
        }
        throw new ApplicationException(MessageFormat.format(BaseMsgConstant.BASE_MSG_ERROR_FORMAT_UNRECOGNIZED, BaseNodeTypeEnum.class, id));
    }

    SyncFlagEnum(int id, String code) {
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
