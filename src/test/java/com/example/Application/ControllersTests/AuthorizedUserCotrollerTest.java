package com.example.Application.ControllersTests;

import com.example.Application.controllers.AuthorizedUserController;
import com.example.Application.service.AuthorizationProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

public class AuthorizedUserCotrollerTest {

    @Mock
    private AuthorizationProcessor authorizationProcessor;

    @InjectMocks
    private AuthorizedUserController authorizedUserController;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAuthorizationIsRemovedWhenDeauthorizeEndpointIsHit(){
        String actualResult = authorizedUserController.deauthorizeUser();
        assertEquals("redirect:/", actualResult);
        verify(authorizationProcessor).deauthorize();
    }

    @Test
    public void testActivitiesRecapEndpointReturnsActivityRecapPageName(){
        String actualResult = authorizedUserController.activitiesWrap();
        assertEquals("activityRecap", actualResult);
    }
}
