package com.lyl.web.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jinghuan.common.util.DateUtil;
import com.lyl.web.base.BaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class BL_CommentVO extends BaseEntity {
    private Integer b2_id;
    private Integer b2_pid;
    private String b2_pName;
    private Integer b0_id;
    private String b0_name;
    private Integer b1_id;
    private String b2_content;
    @Column(name = "`create_time`")
    @DateTimeFormat(pattern = DateUtil.DEFAULT_FORMAT_PATTERN_DATETIME)
    @JsonFormat(pattern = DateUtil.DEFAULT_FORMAT_PATTERN_DATETIME, timezone = DateUtil.DEFAULT_TIME_ZONE_TYPE)
    private Date create_time;
    @Column(name = "`modify_time`")
    @DateTimeFormat(pattern = DateUtil.DEFAULT_FORMAT_PATTERN_DATETIME)
    @JsonFormat(pattern = DateUtil.DEFAULT_FORMAT_PATTERN_DATETIME, timezone = DateUtil.DEFAULT_TIME_ZONE_TYPE)
    private Date modify_time;
    // 评论回复
    private List<BL_CommentVO> subCommentVOs = new ArrayList<>();

    /**排序字段*/
    private String sortString;
}
