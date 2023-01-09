package com.rachel.logindemo.dao.entity;

import lombok.Data;

import java.util.List;

@Data
public class Resources {

    private Integer id;

    private String pattern;

    private List<Role> roles;
}
