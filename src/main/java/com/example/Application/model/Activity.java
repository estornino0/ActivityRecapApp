package com.example.Application.model;

public class Activity {

    private float distance;
    private float elapsed_time;
    private Map map;

    public float getDistanceInKm() {
        return distance / 1000;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public float getElapsedTimeInMinutes() {
        return elapsed_time / 3600;
    }

    public void setElapsed_time(int elapsed_time) {
        this.elapsed_time = elapsed_time;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public Map getMap() {
        return map;
    }

    public static class Map {
        private String summary_polyline;

        public String getSummary_polyline() {
            return summary_polyline;
        }

        public void setSummary_polyline(String summary_polyline) {
            this.summary_polyline = summary_polyline;
        }
    }
}
