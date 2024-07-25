package com.example.Application.service;

import com.example.Application.model.UserSession;
import com.example.Application.model.AuthorizationDetails;
import com.example.Application.stravaProxies.StravaApiAccessProxy;
import com.example.Application.stravaProxies.StravaTokenExchangeProxy;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import java.time.Instant;

@Service
@RequestScope
public class AuthorizationProcessor {

    private final StravaTokenExchangeProxy stravaTokenExchangeProxy;
    private final StravaApiAccessProxy stravaApiAccessProxy;
    private final UserSession userSession;

    public AuthorizationProcessor(StravaTokenExchangeProxy stravaTokenExchangeProxy, StravaApiAccessProxy stravaApiAccessProxy, UserSession userSession) {
        this.stravaTokenExchangeProxy = stravaTokenExchangeProxy;
        this.stravaApiAccessProxy = stravaApiAccessProxy;
        this.userSession = userSession;
    }

    public void authorizeUser(String code, String scope) {
        AuthorizationDetails authorizationDetails = stravaTokenExchangeProxy.doTokenExchange(code);
        userSession.setAccessToken(authorizationDetails.accessToken());
        userSession.setRefreshToken(authorizationDetails.getRefresh_token());
        Instant expiryTime = Instant.ofEpochSecond(authorizationDetails.getExpires_at());
        userSession.setTokenExpiryTime(expiryTime);
    }

    public void deauthorize() {
        stravaApiAccessProxy.deauthorize();
        userSession.removeAuthorization();
    }
}
