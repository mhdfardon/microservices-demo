package com.microservices.demo.services.user.cipher.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class AESCipherConfig {
    @Value("${user.service.aesKey}")
    private String aesKey;
}
