package com.example.Application.model;

import java.util.List;

public class ActivityRecap {

    private float totalDistanceInKm;
    private float totalTimeInHs;
    private int numberOfActivities;
    private List<String> polylines;

    public float getTotalDistanceInKm() {
        return totalDistanceInKm;
    }

    public void setTotalDistanceInKm(float totalDistanceInKm) {
        this.totalDistanceInKm = totalDistanceInKm;
    }

    public float getTotalTimeInHs() {
        return totalTimeInHs;
    }

    public void setTotalTimeInHs(float totalTimeInHs) {
        this.totalTimeInHs = totalTimeInHs;
    }

    public int getNumberOfActivities() {
        return numberOfActivities;
    }

    public void setNumberOfActivities(int numberOfActivities) {
        this.numberOfActivities = numberOfActivities;
    }

    public List<String> getPolylines() {
        return polylines;
    }

    public void setPolylines(List<String> polylines) {
        this.polylines = polylines;
    }
}
