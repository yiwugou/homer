package com.yiwugou.homer.fastjson.test;

import java.io.Serializable;

import lombok.Data;

@Data
public class Foo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String username;
    private String password;

}