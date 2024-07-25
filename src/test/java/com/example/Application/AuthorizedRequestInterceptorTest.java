package com.example.Application;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import com.example.Application.model.UserSession;
import com.example.Application.feignConfig.FeignApiAccessConfiguration;

public class AuthorizedRequestInterceptorTest {

    @Mock
    private UserSession userSession;

    @InjectMocks
    private FeignApiAccessConfiguration feignApiAccessConfiguration;

    private RequestInterceptor authorizedRequestInterceptor;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        authorizedRequestInterceptor = feignApiAccessConfiguration.requestInterceptor();
    }

    @Test
    public void testApplyInterceptorOnNotExpiredAccessToken() {
        // Configura el comportamiento del mock para devolver un token específico
        when(userSession.getAccessToken()).thenReturn("mockedAccessToken");
        when(userSession.isTokenValid()).thenReturn(true);

        // Crea una solicitud simulada
        RequestTemplate requestTemplate = new RequestTemplate();
        authorizedRequestInterceptor.apply(requestTemplate);

        // Verifica que el header Authorization tenga el valor esperado
        assertEquals("Bearer mockedAccessToken", requestTemplate.headers().get("Authorization").iterator().next());
    }

    @Test
    public void testApplyInterceptorOnExpiredAccessToken() {
        when(userSession.getAccessToken()).thenReturn("mockedAccessToken");
        when(userSession.getRefreshToken()).thenReturn("mockedRefreshToken");
        when(userSession.isTokenValid()).thenReturn(false);

        RequestTemplate requestTemplate = new RequestTemplate();
        authorizedRequestInterceptor.apply(requestTemplate);

        assertEquals("${strava.client-id}", requestTemplate.queries().get("client_id").iterator().next());
        assertEquals("${strava.client-secret}", requestTemplate.queries().get("client_secret").iterator().next());
        assertEquals("refresh_token", requestTemplate.queries().get("grant_type").iterator().next());
        assertEquals("mockedRefreshToken", requestTemplate.queries().get("refresh_token").iterator().next());
    }
}
