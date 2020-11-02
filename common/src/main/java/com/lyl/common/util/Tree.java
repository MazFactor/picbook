package com.lyl.common.util;

import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dcc
 * @date 2019-11-08 14:45
 */
public class Tree {

    private Long id;
    /**编码*/
    @ApiModelProperty(value="编码",name="code")
    private String code;

    /**名称*/
    @ApiModelProperty(value="名称",name="name")
    private String name;

    /**层级（0工厂，1车间，2线体，3工位)*/
    @ApiModelProperty(value="层级（0工厂，1车间，2线体，3工位)",name="level")
    private Integer level;

    /**在当前层级下的顺序 */
    @ApiModelProperty(value="在当前层级下的顺序 ",name="seq")
    private Integer seq;

    /**父节点ID （顶级工厂父节点默认为0）*/
    @ApiModelProperty(value="父节点ID （顶级工厂父节点默认为0）",name="parentId")
    private Long parentId;

    private List<Tree> children = new ArrayList<Tree>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public List<Tree> getChildren() {
        return children;
    }

    public void setChildren(List<Tree> children) {
        this.children = children;
    }
}
