package com.example.Application.controllers;

import com.example.Application.service.AuthorizationProcessor;
import com.example.Application.stravaProxies.StravaApiAccessProxy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthorizedUserController {

    private final StravaApiAccessProxy stravaApiAccessProxy;
    private final AuthorizationProcessor authorizationProcessor;

    public AuthorizedUserController(StravaApiAccessProxy stravaApiAccessProxy, AuthorizationProcessor authorizationProcessor) {
        this.stravaApiAccessProxy = stravaApiAccessProxy;
        this.authorizationProcessor = authorizationProcessor;
    }


    @GetMapping("/activityRecap")
    public String activitiesWrap(){
        return "activityRecap";
    }

    @GetMapping("/deauthorize")
    public String deauthorizeUser() {
        authorizationProcessor.deauthorize();
        return "redirect:/";
    }
}
