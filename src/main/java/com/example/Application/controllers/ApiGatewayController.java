package com.example.Application.controllers;

import com.example.Application.model.DateInterval;
import com.example.Application.model.ActivityRecap;
import com.example.Application.service.ActivitiyRecapService;
import com.example.Application.stravaProxies.StravaApiAccessProxy;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "${server.url}")
public class ApiGatewayController {

    private final ActivitiyRecapService activitiyRecapService;

    public ApiGatewayController(StravaApiAccessProxy stravaApiAccessProxy, ActivitiyRecapService activitiyRecapService) {
        this.activitiyRecapService = activitiyRecapService;
    }

    @PostMapping("/activityRecap")
    public ActivityRecap activitiesWrap(@RequestBody DateInterval dateInterval){
        return activitiyRecapService.activitiesWrap(dateInterval);
    }
}
