package com.cs203.g1t4.backend.models.exceptions;

import com.cs203.g1t4.backend.models.queue.QueueingStatusValues;

public class MissingQueueException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public MissingQueueException() {
        super(QueueingStatusValues.MISSING.getStatusName());
    }
}
