package com.example.Application.model;

import java.time.Instant;

public class DateInterval {
    private String after;
    private String before;

    public DateInterval(String after, String before) {
        this.after = after;
        this.before = before;
    }

    public long getAfterEpoch() {
        return Instant.parse(after).getEpochSecond();
    }

    public void setAfter(String after) {
        this.after = after;
    }

    public long getBeforeEpoch() {
        return Instant.parse(before).getEpochSecond();
    }

    public void setBefore(String before) {
        this.before = before;
    }
}
