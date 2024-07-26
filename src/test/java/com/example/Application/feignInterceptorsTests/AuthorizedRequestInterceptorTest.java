package com.example.Application.feignInterceptorsTests;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import com.example.Application.model.UserSession;
import com.example.Application.feignConfig.FeignApiAccessConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.reflect.Field;

public class AuthorizedRequestInterceptorTest {

    @Mock
    private UserSession userSession;

    @InjectMocks
    private FeignApiAccessConfiguration feignApiAccessConfiguration;

    private RequestInterceptor authorizedRequestInterceptor;

    private RequestTemplate requestTemplate;

    private final String clientId = "test-client-id";
    private final String clientSecret = "test-client-secret";

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        setPrivateField(feignApiAccessConfiguration, "clientId", clientId);
        setPrivateField(feignApiAccessConfiguration, "clientSecret", clientSecret);
        authorizedRequestInterceptor = feignApiAccessConfiguration.requestInterceptor();
        requestTemplate = new RequestTemplate();
    }

    private void setPrivateField(Object target, String fieldName, String value) {
        try {
            Field field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testApplyInterceptorOnNotExpiredAccessToken() {
        when(userSession.getAccessToken()).thenReturn("mockedAccessToken");
        when(userSession.isTokenValid()).thenReturn(true);

        authorizedRequestInterceptor.apply(requestTemplate);

        assertEquals("Bearer mockedAccessToken", requestTemplate.headers().get("Authorization").iterator().next());
    }

    @Test
    public void testApplyInterceptorOnExpiredAccessToken() {
        when(userSession.getAccessToken()).thenReturn("mockedAccessToken");
        when(userSession.getRefreshToken()).thenReturn("mockedRefreshToken");
        when(userSession.isTokenValid()).thenReturn(false);

        authorizedRequestInterceptor.apply(requestTemplate);

        assertEquals(clientId, requestTemplate.queries().get("client_id").iterator().next());
        assertEquals(clientSecret, requestTemplate.queries().get("client_secret").iterator().next());
        assertEquals("refresh_token", requestTemplate.queries().get("grant_type").iterator().next());
        assertEquals("mockedRefreshToken", requestTemplate.queries().get("refresh_token").iterator().next());
    }
}
