package com.herbalife.is.mybatisencrypt.entity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString
@Builder
public class Student {
    private Integer id;
    private String name;
    private Integer age;
    private String cellphone;
}
