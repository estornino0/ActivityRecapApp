package com.example.Application.stravaProxies;

import com.example.Application.feignConfig.FeignTokenExchangeConfiguration;
import com.example.Application.model.AuthorizationDetails;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "tokenExchange", url = "${strava.url}", configuration = FeignTokenExchangeConfiguration.class)
public interface StravaTokenExchangeProxy {

    @PostMapping("oauth/token")
    AuthorizationDetails doTokenExchange(
            @RequestParam String code);
}
