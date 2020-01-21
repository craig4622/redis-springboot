package com.xsq.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName User
 * @Description TODO
 * @Author xsq
 * @Date 2020/1/21 9:59
 **/
@Data
public class User implements Serializable {
    private static final long serialVersionUID = 2893241843150538173L;
    private String  name;

    private Integer age;
}
