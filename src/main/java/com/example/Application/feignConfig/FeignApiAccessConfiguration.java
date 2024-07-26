package com.example.Application.feignConfig;

import com.example.Application.model.UserSession;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

public class FeignApiAccessConfiguration {

    @Value("${strava.client-id}")
    private String clientId;

    @Value("${strava.client-secret}")
    private String clientSecret;

    private UserSession userSession;

    public FeignApiAccessConfiguration(UserSession userSession) {
        this.userSession = userSession;
    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate requestTemplate) {
                if(userSession.isTokenValid()) {
                    requestTemplate.header("Authorization", "Bearer " + userSession.getAccessToken());
                } else {
                    System.out.println(clientId);
                    requestTemplate.query("client_id", clientId);
                    requestTemplate.query("client_secret", clientSecret);
                    requestTemplate.query("grant_type", "refresh_token");
                    requestTemplate.query("refresh_token", userSession.getRefreshToken());
                }
            }
        };
    }
}
