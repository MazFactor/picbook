package com.lyl.web.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jinghuan.common.util.DateUtil;
import com.lyl.web.base.BaseEntity;
import com.lyl.web.entity.BL_User;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "`bl_article`")
public class BL_ArticleIndexVO extends BaseEntity {
    @Column(name = "`B1_ID`")
    private Integer b1_id;
    private Integer b0_id;
    private Integer b4_id;
    private BL_User bl_user;
    private String b1_thematic;
    private String b1_subject;
    private String b1_content;
//    private Integer b2_id;
    @Column(name = "`create_time`")
    @DateTimeFormat(pattern = DateUtil.DEFAULT_FORMAT_PATTERN_DATETIME)
    @JsonFormat(pattern = DateUtil.DEFAULT_FORMAT_PATTERN_DATETIME, timezone = DateUtil.DEFAULT_TIME_ZONE_TYPE)
    private Date create_time;
    @Column(name = "`modify_time`")
    //@DateTimeFormat(pattern = DateUtil.DEFAULT_FORMAT_PATTERN_DATETIME)
    //@JsonFormat(pattern = DateUtil.DEFAULT_FORMAT_PATTERN_DATETIME, timezone = DateUtil.DEFAULT_TIME_ZONE_TYPE)
    private Date modify_time;
    private Integer b1_expose;
    private String b1_tags;
    private List<BL_TagVO> tags;
//    private Integer b3_id;
    private Integer b1_commentsCount;
    /**排序字段*/
    private String sortString;

    private String ftpImageParent;
    private String targetImagePath;
}
