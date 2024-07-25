package com.example.Application.securityFilters;

import com.example.Application.model.UserSession;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class RedirectUnauthorizedFilter extends HttpFilter {

    private final UserSession userSession;

    public RedirectUnauthorizedFilter(UserSession userSession) {
        this.userSession = userSession;
    }

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (!userSession.authorizationGranted()) {
            response.sendRedirect("/");
        } else {
            chain.doFilter(request, response);
        }
    }
}