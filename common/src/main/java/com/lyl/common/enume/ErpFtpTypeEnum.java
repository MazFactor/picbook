package com.lyl.common.enume;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.lyl.common.constant.BaseMsgConstant;
import com.lyl.common.exception.ApplicationException;
import com.lyl.common.serializer.BaseEnumSerializer;


import java.text.MessageFormat;


/**
 * ERP的FTP文件夹类型枚举
 *
 * @author WRQ
 * @date 2019/4/16
 * @since 1.0.0
 */
@JsonSerialize(using = BaseEnumSerializer.class)
public enum ErpFtpTypeEnum{


    BOM(0, "bom"),
    MATERIAL(1, "material"),
    PLANAGREEMENT(2,"planAgreement"),
    REPERTORY(3,"repertory"),
    RECEIVING(4,"receiving"),
    MOVESTORAGE (5,"moveStorage"),
    PRODUCTSSTORAGE (6,"productsStorage");

    private Integer id;
    private String name;

    @JsonCreator
    public static ErpFtpTypeEnum getItem(Integer id) {
        for (ErpFtpTypeEnum item : values()) {
            if (item.getId().equals(id)) {
                return item;
            }
        }
        throw new ApplicationException(MessageFormat.format(BaseMsgConstant.BASE_MSG_ERROR_FORMAT_UNRECOGNIZED, BaseNodeTypeEnum.class, id));
    }

    ErpFtpTypeEnum(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }
    public String getName() {
        return name;
    }
}