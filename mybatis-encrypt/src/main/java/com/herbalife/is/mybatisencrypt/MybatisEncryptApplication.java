package com.herbalife.is.mybatisencrypt;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.herbalife.is.mybatisencrypt.mapper")
public class MybatisEncryptApplication {

    public static void main(String[] args) {
        SpringApplication.run(MybatisEncryptApplication.class, args);
    }

}
