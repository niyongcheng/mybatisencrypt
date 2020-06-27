package com.herbalife.is.mybatisencrypt.request;

import com.herbalife.is.mybatisencrypt.annotation.Cellphone;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString
@Builder
public class StudentRequest {
    private Integer id;
    private String name;
    private Integer age;

    @Cellphone
    private String cellphone;

    @Cellphone
    private String mobilephone;
}
