package com.example.Application.stravaProxies;

import com.example.Application.feignConfig.FeignApiAccessConfiguration;
import com.example.Application.model.Activity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "apiAccess", url = "${strava.url}", configuration = FeignApiAccessConfiguration.class)
public interface StravaApiAccessProxy {

    @GetMapping("/api/v3/athlete/activities")
    List<Activity> getActivitiesBetweenDates(
            @RequestParam(value = "after") long after,
            @RequestParam(value = "before") long before,
            @RequestParam(value = "page") int page);

    @PostMapping("/oauth/deauthorize")
    void deauthorize();
}
