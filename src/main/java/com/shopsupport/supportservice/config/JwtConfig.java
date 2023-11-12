package com.shopsupport.supportservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.core.io.FileSystemResource;
@Configuration
public class JwtConfig {


    @Bean
    public String getJwtPrivateKey() throws IOException {
        Resource resource = new FileSystemResource("/Users/michau/Documents/praca_inz/support-service/keys/private-key.pem");
        return new String(Files.readAllBytes(resource.getFile().toPath()));
    }


}