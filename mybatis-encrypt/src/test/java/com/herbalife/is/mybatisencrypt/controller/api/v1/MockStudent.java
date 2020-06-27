package com.herbalife.is.mybatisencrypt.controller.api.v1;

import com.herbalife.is.mybatisencrypt.entity.Student;
import com.herbalife.is.mybatisencrypt.request.StudentRequest;

public class MockStudent {
    public static StudentRequest findStudentById(){
        return StudentRequest.builder()
                .age(10)
                .cellphone("13671727893")
                .id(1)
                .name("sid")
                .build();
    }
}
