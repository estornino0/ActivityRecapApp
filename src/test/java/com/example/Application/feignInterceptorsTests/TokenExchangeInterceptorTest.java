package com.example.Application.feignInterceptorsTests;

import com.example.Application.feignConfig.FeignTokenExchangeConfiguration;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TokenExchangeInterceptorTest {

    private final String clientId = "test-client-id";
    private final String clientSecret = "test-client-secret";

    private RequestInterceptor tokenExchangeInterceptor;

    private RequestTemplate requestTemplate;

    @BeforeEach
    public void setUp(){
        FeignTokenExchangeConfiguration feignApiAccessConfiguration = new FeignTokenExchangeConfiguration();
        setPrivateField(feignApiAccessConfiguration, "clientId", clientId);
        setPrivateField(feignApiAccessConfiguration, "clientSecret", clientSecret);
        tokenExchangeInterceptor = feignApiAccessConfiguration.requestInterceptor();
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
    public void testTokenExchangeInterceptorAddsClientIdSecretAndGrantTypeToRequest(){

        tokenExchangeInterceptor.apply(requestTemplate);

        assertEquals(clientId, requestTemplate.queries().get("client_id").iterator().next());
        assertEquals(clientSecret, requestTemplate.queries().get("client_secret").iterator().next());
        assertEquals("authorization_code", requestTemplate.queries().get("grant_type").iterator().next());
    }
}
