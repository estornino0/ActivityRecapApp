package com.example.Application.feignConfig;

import com.example.Application.model.UserSession;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;

public class FeignApiAccessConfiguration {

    private UserSession userSession;

    public FeignApiAccessConfiguration(UserSession userSession) {
        this.userSession = userSession;
    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate requestTemplate) {
//                requestTemplate.header("Authorization", "Bearer " + "e1afaedc665e8ad1a280f47af612001e8ee33081");

                if(userSession.isTokenValid()) {
                    requestTemplate.header("Authorization", "Bearer " + userSession.getAccessToken());
                } else {
                    requestTemplate.query("client_id", "${strava.client-id}");
                    requestTemplate.query("client_secret", "${strava.client-secret}");
                    requestTemplate.query("grant_type", "refresh_token");
                    requestTemplate.query("refresh_token", userSession.getRefreshToken());
                }
            }
        };
    }
}
