package com.eventsourcing.cqrs.bankaccountcorecqrs.exceptions;

public class ConcurrencyException extends RuntimeException {
    public ConcurrencyException(String message) {
        super(message);
    }
}
