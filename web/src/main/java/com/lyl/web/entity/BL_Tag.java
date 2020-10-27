package com.lyl.web.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jinghuan.common.util.DateUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

@Getter
@Setter
@ToString
@Table(name = "`bl_tag`")
public class BL_Tag {
    private Integer b3_id;
    private String b3_name;
    @Column(name = "`create_time`")
    @DateTimeFormat(pattern = DateUtil.DEFAULT_FORMAT_PATTERN_DATETIME)
    @JsonFormat(pattern = DateUtil.DEFAULT_FORMAT_PATTERN_DATETIME, timezone = DateUtil.DEFAULT_TIME_ZONE_TYPE)
    private Date create_time;

    @Column(name = "`modify_time`")
    //@DateTimeFormat(pattern = DateUtil.DEFAULT_FORMAT_PATTERN_DATETIME)
    //@JsonFormat(pattern = DateUtil.DEFAULT_FORMAT_PATTERN_DATETIME, timezone = DateUtil.DEFAULT_TIME_ZONE_TYPE)
    private Date modify_time;

}
