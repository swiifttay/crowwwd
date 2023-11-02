package com.cs203.g1t4.backend.models.queue;
public enum QueueingStatusValues {
    OK("OK"),
    PENDING("PENDING"),
    HOLDING("HOLDING"),
    MISSING("MISSING");

    private String statusName;

    QueueingStatusValues(String statusName) {
        this.statusName = statusName;
    }

    public String getStatusName() {
        return statusName;
    }
}

