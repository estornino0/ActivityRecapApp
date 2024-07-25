package com.example.Application.securityFilters;

import com.example.Application.model.UserSession;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<RedirectUnauthorizedFilter> authorizationFilter(UserSession userSession) {
        FilterRegistrationBean<RedirectUnauthorizedFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new RedirectUnauthorizedFilter(userSession));
        registrationBean.addUrlPatterns("/activityRecap");
        return registrationBean;
    }
}
