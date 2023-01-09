package com.rachel.logindemo.dao.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Role implements Serializable {
    private static final long serialVersionUID = 825384782616737527L;

    private Integer id;

    private String name;

    private String description;
}
