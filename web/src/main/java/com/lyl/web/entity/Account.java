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
@Table(name = "`account`")
public class Account {
    private Integer account_id;
    private String account;
    private String password;
    @Column(name = "`create_time`")
    @DateTimeFormat(pattern = com.lyl.common.util.DateUtil.DEFAULT_FORMAT_PATTERN_DATETIME)
    @JsonFormat(pattern = com.lyl.common.util.DateUtil.DEFAULT_FORMAT_PATTERN_DATETIME, timezone = com.lyl.common.util.DateUtil.DEFAULT_TIME_ZONE_TYPE)
    private Date create_time;

    @Column(name = "`modify_time`")
    //@DateTimeFormat(pattern = DateUtil.DEFAULT_FORMAT_PATTERN_DATETIME)
    //@JsonFormat(pattern = DateUtil.DEFAULT_FORMAT_PATTERN_DATETIME, timezone = DateUtil.DEFAULT_TIME_ZONE_TYPE)
    private Date modify_time;
}
