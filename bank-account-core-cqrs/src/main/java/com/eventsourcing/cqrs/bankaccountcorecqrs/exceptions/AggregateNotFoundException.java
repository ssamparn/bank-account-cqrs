package com.eventsourcing.cqrs.bankaccountcorecqrs.exceptions;

public class AggregateNotFoundException extends RuntimeException {
    public AggregateNotFoundException(String message) {
        super(message);
    }
}
