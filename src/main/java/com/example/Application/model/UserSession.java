package com.example.Application.model;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;
import java.time.Instant;

@Service
@SessionScope
public class UserSession {

    private String accessToken;
    private String refreshToken;
    private Instant tokenExpiryTime;
    private boolean authorizationGranted;

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        this.grantAuthorization();
    }

    public String getAccessToken(){
        return accessToken;
    }

    public boolean authorizationGranted() {
        return authorizationGranted;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Instant getTokenExpiryTime() {
        return tokenExpiryTime;
    }

    public void setTokenExpiryTime(Instant tokenExpiryTime) {
        this.tokenExpiryTime = tokenExpiryTime;
    }

    public boolean isTokenValid() {
        return tokenExpiryTime.isAfter(Instant.now());
    }

    public void grantAuthorization(){
        authorizationGranted = true;
    }

    public void removeAuthorization() {
        authorizationGranted = false;
    }
}
