package com.lyl.web.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

@Getter
@Setter
@Data
@Table(name = "`bl_user`")
public class BL_User {
    private Integer b0_id;
    private String b0_name;
    private String b0_password;
    private boolean b0_gender;
    private String b0_phone;
    private String b0_email;
    @Column(name = "`create_time`")
    //@DateTimeFormat(pattern = DateUtil.DEFAULT_FORMAT_PATTERN_DATETIME)
    //@JsonFormat(pattern = DateUtil.DEFAULT_FORMAT_PATTERN_DATETIME, timezone = DateUtil.DEFAULT_TIME_ZONE_TYPE)
    private Date create_time;
    @Column(name = "`create_time`")
    //@DateTimeFormat(pattern = DateUtil.DEFAULT_FORMAT_PATTERN_DATETIME)
    //@JsonFormat(pattern = DateUtil.DEFAULT_FORMAT_PATTERN_DATETIME, timezone = DateUtil.DEFAULT_TIME_ZONE_TYPE)
    private Date modify_time;

//    public BL_User(Integer b0_id, String b0_name, String b0_password){
//        this.b0_id=b0_id;
//        this.b0_name=b0_name;
//        this.b0_password=b0_password;
//    }
}
