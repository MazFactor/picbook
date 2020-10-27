package com.jinghuan.common.enume;
/**
 * 业务系统标识 枚举类
 * @说明:
 * @Author: chenlg
 * @Date: 2019/7/11 15:56
 * @Version 1.0
 */
public enum SuplierTypeEnum {

    MDM("MDM", "MDM"),
    EAS("EAS", "EAS"),
    LES("LES", "LES"),
    SRM("SRM", "SRM"),
    SIP("SIP", "SIP"),
    TMS("P-TMS", "P-TMS"),
    WMS("WMS", "WMS"),
    LMS("LMS", "LMS"),
    MES("MES", "MES");

    private String code;
    private String name;


    public static SuplierTypeEnum getItem(String code) {
        for (SuplierTypeEnum item : values()) {
            if (item.getCode().equals(code)) {
                return item;
            }else {
                return null;
            }
        }
        return null;
    }

    SuplierTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }}
