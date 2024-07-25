package com.example.Application;

import com.example.Application.controllers.AuthorizedUserController;
import com.example.Application.service.AuthorizationProcessor;
import com.example.Application.stravaProxies.StravaApiAccessProxy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

public class AuthorizedUserCotrollerTest {

    @Mock
    private StravaApiAccessProxy stravaApiAccessProxy;

    @Mock
    private AuthorizationProcessor authorizationProcessor;

    @InjectMocks
    private AuthorizedUserController authorizedUserController;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testauthorizationIsRemovedWhenDeauthorizeEndpointIsHit(){

        String result = authorizedUserController.deauthorizeUser();
        assertEquals("redirect:/", result);
        verify(authorizationProcessor).deauthorize();
    }
}
