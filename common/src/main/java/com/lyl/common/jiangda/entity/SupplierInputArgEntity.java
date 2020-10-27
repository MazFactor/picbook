package com.jinghuan.common.jiangda.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 供应商接口查询输入参数实体
 * @Author: chenlg
 * @Date: 2019/7/10 17:37
 * @Version 1.0
 */
@ApiModel(value="SupplierInputArgEntity",description="供应商接口查询输入参数实体")
public class SupplierInputArgEntity implements Serializable {


    private static final long serialVersionUID = 397668214994843057L;


    public SupplierInputArgEntity() {

    }

    @ApiModelProperty(value="系统标识",name="sysId",required=true)
    private String sysId;

    @ApiModelProperty(value="开始时间",name="startDate",example="yyyy-MM-dd HH:mm:ss",required=true)
    protected String startDate;

    @ApiModelProperty(value="结束时间",name="entDate",example="yyyy-MM-dd HH:mm:ss",required=true)
    protected String entDate;


    /**
     * 系统标识
     * @return
     */
    public String getSysId() {
        return sysId;
    }

    /**
     * 系统标识
     * @param sysId
     */
    public void setSysId(String sysId) {
        this.sysId = sysId;
    }

    /**
     * 开始时间
     * @return
     */
    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * 结束时间
     * @return
     */
    public String getEntDate() {
        return entDate;
    }

    public void setEntDate(String entDate) {
        this.entDate = entDate;
    }


}
