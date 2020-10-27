package com.lyl.web.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jinghuan.common.util.DateUtil;
import com.lyl.web.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import java.util.Date;

@Getter
@Setter
@Data
@EqualsAndHashCode(callSuper = false)
public class BL_CategoryVO extends BaseEntity {
    private Integer b4_id;
    private String b4_name;
    @Column(name = "`create_time`")
    @DateTimeFormat(pattern = DateUtil.DEFAULT_FORMAT_PATTERN_DATETIME)
    @JsonFormat(pattern = DateUtil.DEFAULT_FORMAT_PATTERN_DATETIME, timezone = DateUtil.DEFAULT_TIME_ZONE_TYPE)
    private Date create_time;
    @Column(name = "`modify_time`")
    @DateTimeFormat(pattern = DateUtil.DEFAULT_FORMAT_PATTERN_DATETIME)
    @JsonFormat(pattern = DateUtil.DEFAULT_FORMAT_PATTERN_DATETIME, timezone = DateUtil.DEFAULT_TIME_ZONE_TYPE)
    private Date modify_time;
    private Integer articleCount;
    /**排序字段*/
    private String sortString;
}
