package com.lyl.web.vo;

import com.lyl.web.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@EqualsAndHashCode(callSuper = false)
public class BL_ArticleArchiveVO extends BaseEntity {
    private String timeString;
    private Integer sumCount;
    /**排序字段*/
    private String sortString;
}
