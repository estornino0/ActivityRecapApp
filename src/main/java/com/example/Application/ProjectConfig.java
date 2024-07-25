package com.example.Application;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients( basePackages = "com.example.Application")
public class ProjectConfig {
}
