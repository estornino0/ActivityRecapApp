package com.example.Application.model;

public class AuthorizationDetails {

    private String access_token;
    private int expires_at;
    private String refresh_token;

    public void setAccess_token(String accessToken){
        access_token = accessToken;
    }

    public String accessToken(){
        return access_token;
    }

    public int getExpires_at() {
        return expires_at;
    }

    public void setExpires_at(int expires_at) {
        this.expires_at = expires_at;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }
}
