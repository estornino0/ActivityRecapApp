package com.example.Application.service;

import com.example.Application.model.DateInterval;
import com.example.Application.model.Activity;
import com.example.Application.model.ActivityRecap;
import com.example.Application.stravaProxies.StravaApiAccessProxy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ActivitiyRecapService {

    private final StravaApiAccessProxy stravaApiAccessProxy;

    public ActivitiyRecapService(StravaApiAccessProxy stravaApiAccessProxy){
        this.stravaApiAccessProxy = stravaApiAccessProxy;
    }

    public ActivityRecap activitiesWrap(DateInterval dateInterval) {
        ActivityRecap activityRecap = new ActivityRecap();

        List<Activity> activities = getActivities(dateInterval.getAfterEpoch(), dateInterval.getBeforeEpoch());

        activityRecap.setNumberOfActivities(activities.size());
        activityRecap.setTotalDistanceInKm(totalDistanceInKm(activities));
        activityRecap.setTotalTimeInHs(totalTimeInHs(activities));
        activityRecap.setPolylines(polylines(activities));
        return activityRecap;
    }

    private List<Activity> getActivities(long after, long before) {
        int page = 1;
        List<Activity> activities = new ArrayList<>();
        List<Activity> activitiesOnPage;
        do {
            activitiesOnPage = stravaApiAccessProxy.getActivitiesBetweenDates(after, before, page);
            activities.addAll(activitiesOnPage);
            page++;
        } while (!activitiesOnPage.isEmpty());
        return activities;
    }

    private float totalDistanceInKm(List<Activity> activities) {
        float total = 0;
        for(Activity activity: activities){
            total += activity.getDistanceInKm();
        }
        return total;
    }

    private float totalTimeInHs(List<Activity> activities) {
        float total = 0;
        for(Activity activity: activities){
            total += activity.getElapsedTimeInMinutes();
        }
        return total;
    }

    private List<String> polylines(List<Activity> activities) {
        List<String> polylines = new ArrayList<>();
        for(Activity activity: activities){
            polylines.add(activity.getMap().getSummary_polyline());
        }
        return polylines;
    }

}
