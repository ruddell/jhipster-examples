package com.mycompany.myapp.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.mycompany.myapp")
public class FeignConfiguration {

}
