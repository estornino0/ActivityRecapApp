package com.example.Application.ControllersTests;

import com.example.Application.controllers.ApiGatewayController;
import com.example.Application.model.ActivityRecap;
import com.example.Application.model.DateInterval;
import com.example.Application.service.ActivitiyRecapService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ApiGatewayControllerTest {

    @Mock
    private ActivitiyRecapService activitiyRecapService;

    @InjectMocks
    private ApiGatewayController apiGatewayController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testActivitiesRecapControllerCallsActivityRecapService(){
        ActivityRecap expectedResponse = new ActivityRecap();
        DateInterval dateInterval = new DateInterval("2023-01-01", "2024-01-01");
        when(activitiyRecapService.activitiesWrap(dateInterval)).thenReturn(expectedResponse);

        ActivityRecap actualResponse = apiGatewayController.activitiesWrap(dateInterval);
        assertEquals(expectedResponse, actualResponse);
    }
}
