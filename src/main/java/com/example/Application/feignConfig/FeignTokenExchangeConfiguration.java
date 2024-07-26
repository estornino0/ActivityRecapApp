package com.example.Application.feignConfig;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class FeignTokenExchangeConfiguration {

    @Value("${strava.client-id}")
    private String clientId;

    @Value("${strava.client-secret}")
    private String clientSecret;

    @Bean
    public RequestInterceptor requestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate requestTemplate) {
                requestTemplate.query("client_id", clientId);
                requestTemplate.query("client_secret", clientSecret);
                requestTemplate.query("grant_type", "authorization_code");
            }
        };
    }
}
