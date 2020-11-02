package com.lyl.web.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
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
@Table(name = "`article`")
public class Article {
    private Integer article_id;
    private String title;
    private Integer pic_id;
    private Picture picture;
    private String brief;
    @Column(name = "`create_time`")
    @DateTimeFormat(pattern = com.lyl.common.util.DateUtil.DEFAULT_FORMAT_PATTERN_DATETIME)
    @JsonFormat(pattern = com.lyl.common.util.DateUtil.DEFAULT_FORMAT_PATTERN_DATETIME, timezone = com.lyl.common.util.DateUtil.DEFAULT_TIME_ZONE_TYPE)
    private Date create_time;

    @Column(name = "`modify_time`")
    //@DateTimeFormat(pattern = DateUtil.DEFAULT_FORMAT_PATTERN_DATETIME)
    //@JsonFormat(pattern = DateUtil.DEFAULT_FORMAT_PATTERN_DATETIME, timezone = DateUtil.DEFAULT_TIME_ZONE_TYPE)
    private Date modify_time;
}
