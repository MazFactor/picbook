package com.lyl.web.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Table;

@Getter
@Setter
@ToString
@Table(name = "`bl_article`")
public class User {
    private String name;
    private int age;
}
