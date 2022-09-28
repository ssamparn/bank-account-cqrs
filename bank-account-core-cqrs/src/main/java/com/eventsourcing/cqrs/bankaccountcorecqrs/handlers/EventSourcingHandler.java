package com.eventsourcing.cqrs.bankaccountcorecqrs.handlers;

import com.eventsourcing.cqrs.bankaccountcorecqrs.domain.AggregateRoot;

public interface EventSourcingHandler<T> {
    void save(AggregateRoot aggregate);
    T getByAggregateId(String aggregateId);
    void republishEvents();
}
