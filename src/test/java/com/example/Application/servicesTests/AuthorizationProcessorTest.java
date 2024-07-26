package com.example.Application.servicesTests;

import com.example.Application.feignConfig.FeignApiAccessConfiguration;
import com.example.Application.model.AuthorizationDetails;
import com.example.Application.service.AuthorizationProcessor;
import com.example.Application.model.UserSession;
import com.example.Application.stravaProxies.StravaApiAccessProxy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.Application.stravaProxies.StravaTokenExchangeProxy;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class AuthorizationProcessorTest {

    @Mock
    private StravaTokenExchangeProxy stravaTokenExchangeProxy;

    @Mock
    private StravaApiAccessProxy stravaApiAccessProxy;

    @Autowired
    private FeignApiAccessConfiguration feignApiAccessConfiguration;

    private UserSession userSession;
    private AuthorizationProcessor authorizationProcessor;

    @BeforeEach
    public void setup() {
        userSession = new UserSession();
        MockitoAnnotations.openMocks(this);
        authorizationProcessor = new AuthorizationProcessor(stravaTokenExchangeProxy, stravaApiAccessProxy, userSession);
    }

    @Test
    public void testAthorizationProcesorModifiesSessionWhenAuthorizationGranted() {
        AuthorizationDetails authorizationDetails = new AuthorizationDetails();
        authorizationDetails.setAccess_token("accessToken");
        authorizationDetails.setRefresh_token("refreshToken");
        int expiresAt = (int) Instant.now().getEpochSecond() + 100;
        authorizationDetails.setExpires_at(expiresAt);

        when(stravaTokenExchangeProxy.doTokenExchange("code")).thenReturn(authorizationDetails);

        authorizationProcessor.authorizeUser("code", "scope");

        assertTrue(userSession.isTokenValid());
        assertEquals("accessToken", userSession.getAccessToken());
        assertEquals("refreshToken", userSession.getRefreshToken());
        assertEquals(Instant.ofEpochSecond(expiresAt), userSession.getTokenExpiryTime());
        assertTrue(userSession.isTokenValid());
    }

    @Test
    public void testTokenIsInvalidWhenExpirationTimeIsInThePast() {
        AuthorizationDetails authorizationDetails = new AuthorizationDetails();
        int expiresAt = 100;
        authorizationDetails.setExpires_at(expiresAt);

        when(stravaTokenExchangeProxy.doTokenExchange("code")).thenReturn(authorizationDetails);

        authorizationProcessor.authorizeUser("code", "scope");

        assertFalse(userSession.isTokenValid());
    }

    @Test
    public void testAuthorizationProcessorRevoquesAuthorizationWhenDeauthoriseUserIsCalled() {
        userSession.setAccessToken("accessToken");

        authorizationProcessor.deauthorize();
        assertFalse(userSession.authorizationGranted());
    }
}
