package com.example.Application.ControllersTests;

import com.example.Application.controllers.UnauthorizedUserController;
import com.example.Application.service.AuthorizationProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

public class UnauthorizedUserControllerTest{

    @Mock
    private AuthorizationProcessor authorizationProcessor;

    @InjectMocks
    private UnauthorizedUserController unauthorizedUserController;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testHittingIndexReturnsIndexPageName(){
        String actualResult = unauthorizedUserController.index();
        assertEquals("index", actualResult);
    }

    @Test
    public void testHittingOauthStartsUserAuthorizationAndReturnsRedirect(){
        String code = "code";
        String scope = "scope";
        String actualResult = unauthorizedUserController.auth(code, scope);
        assertEquals("redirect:/activityRecap", actualResult);
        verify(authorizationProcessor).authorizeUser(code, scope);
    }


}