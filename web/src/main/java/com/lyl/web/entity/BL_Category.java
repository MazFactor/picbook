package com.lyl.web.entity;


import javax.persistence.Column;
import java.util.Date;

public class BL_Category {

    private Long b3_id;

    private Long b2_id;

    private String b3_category_name;

    @Column(name = "`create_time`")
    //@DateTimeFormat(pattern = DateUtil.DEFAULT_FORMAT_PATTERN_DATETIME)
    //@JsonFormat(pattern = DateUtil.DEFAULT_FORMAT_PATTERN_DATETIME, timezone = DateUtil.DEFAULT_TIME_ZONE_TYPE)
    private Date b3_create_time;

    private Long b3_article_count;
}
