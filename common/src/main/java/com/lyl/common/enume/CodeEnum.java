package com.jinghuan.common.enume;

public enum CodeEnum {

    SUCCESS(0, "成功"),FAILURE(1, "失败");

    private Integer id;
    private String name;

    CodeEnum(Integer id, String name) {
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
