package com.example.Application;

import com.example.Application.model.DateInterval;
import com.example.Application.model.Activity;
import com.example.Application.model.ActivityRecap;
import com.example.Application.service.ActivitiyRecapService;
import com.example.Application.stravaProxies.StravaApiAccessProxy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ActivitiyRecapServiceTest {

    @InjectMocks
    private ActivitiyRecapService activitiyRecapService;

    @Mock
    private StravaApiAccessProxy stravaApiAccessProxy;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testActivitiesWrapReturnsWrapSuccessfully(){
        String after = "2023-01-01T00:00:00Z";
        String before = "2024-01-01T00:00:00Z";
        long afterEpoch = Instant.parse(after).getEpochSecond();
        long beforeEpoch = Instant.parse(before).getEpochSecond();
        Activity activity1 = newDetailedActivity(8000, 3600, "polyline1");
        Activity activity2 = newDetailedActivity(20000, 3600, "polyline2");
        Activity activity3 = newDetailedActivity(2000, 3600, "polyline3");

        when(stravaApiAccessProxy.getActivitiesBetweenDates(afterEpoch, beforeEpoch, 1)).thenReturn(List.of(activity1, activity2, activity3));

        DateInterval dateInterval = new DateInterval(after, before);
        ActivityRecap activityRecap = activitiyRecapService.activitiesWrap(dateInterval);
        assertEquals(30, activityRecap.getTotalDistanceInKm());
        assertEquals(3, activityRecap.getTotalTimeInHs());
        assertEquals(3, activityRecap.getNumberOfActivities());
        assertEquals(List.of("polyline1", "polyline2", "polyline3"), activityRecap.getPolylines());
    }

    private static Activity newDetailedActivity(int distance, int elapsedTime, String polyline) {
        Activity activity = new Activity();
        activity.setDistance(distance);
        activity.setElapsed_time(elapsedTime);
        Activity.Map map = new Activity.Map();
        map.setSummary_polyline(polyline);
        activity.setMap(map);
        return activity;
    }

    @Test
    public void testActivitiesWrapWhenActivitiesFillMoreThanOneStravaApiPageReturnsWrapSuccessfully(){
        String after = "2023-01-01T00:00:00Z";
        String before = "2024-01-01T00:00:00Z";
        long afterEpoch = Instant.parse(after).getEpochSecond();
        long beforeEpoch = Instant.parse(before).getEpochSecond();
        Activity activity1 = newDetailedActivity(8000, 3600, "polyline1");
        Activity activity2 = newDetailedActivity(20000, 3600, "polyline2");
        Activity activity3 = newDetailedActivity(2000, 3600, "polyline3");

        when(stravaApiAccessProxy.getActivitiesBetweenDates(afterEpoch, beforeEpoch, 1)).thenReturn(List.of(activity1, activity2));
        when(stravaApiAccessProxy.getActivitiesBetweenDates(afterEpoch, beforeEpoch, 2)).thenReturn(List.of(activity3));
        when(stravaApiAccessProxy.getActivitiesBetweenDates(afterEpoch, beforeEpoch, 3)).thenReturn(List.of());

        DateInterval dateInterval = new DateInterval(after, before);
        ActivityRecap activityRecap = activitiyRecapService.activitiesWrap(dateInterval);
        assertEquals(30, activityRecap.getTotalDistanceInKm());
        assertEquals(3, activityRecap.getTotalTimeInHs());
        assertEquals(3, activityRecap.getNumberOfActivities());
        assertEquals(List.of("polyline1", "polyline2", "polyline3"), activityRecap.getPolylines());
    }
}
