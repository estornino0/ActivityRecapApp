package com.example.Application.controllers;

import com.example.Application.service.AuthorizationProcessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UnauthorizedUserController {

    private final AuthorizationProcessor authorizationProcessor;

    public UnauthorizedUserController(AuthorizationProcessor authorizationProcessor) {
        this.authorizationProcessor = authorizationProcessor;
    }

    @GetMapping
    public String index(){
        return "index";
    }

    @GetMapping("/oauth")
    public String auth(@RequestParam String code, @RequestParam String scope){
        authorizationProcessor.authorizeUser(code, scope);
        return "redirect:/activityRecap";
    }

}
