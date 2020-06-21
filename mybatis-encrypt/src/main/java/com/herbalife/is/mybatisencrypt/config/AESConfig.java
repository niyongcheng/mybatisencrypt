package com.herbalife.is.mybatisencrypt.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class AESConfig {

    @Value("${aes.password}")
    private String aesPassword;
}
