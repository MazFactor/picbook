package com.lyl.web.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public abstract class BaseEntity<ID> implements Serializable {
    private static final long serialVersionUID = -8075827049184773786L;
    // 当前页
    protected int page = 1;
    // 每页大小
    protected int size = 4;

    public BaseEntity() {
    }

    public abstract int hashCode();

    public abstract boolean equals(Object var1);

    @JsonIgnore
    public int getPage() {
        return this.page;
    }

    @JsonProperty
    public void setPage(int page) {
        this.page = page;
    }

    @JsonIgnore
    public int getSize() {
        return this.size;
    }

    @JsonProperty
    public void setSize(int size) {
        this.size = size;
    }

    public void setDeleteFlag(Boolean deleteFlag) {
    }

    @JsonIgnore
    public String orderByString() {
        return "`id` DESC";
    }
}

