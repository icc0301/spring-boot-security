package com.xzm.springbootsecurity.entity;

import lombok.Data;

@Data
public class PermissionEntity {

    private Integer id;

    private String name;

    private String path;

    private String url;
}
