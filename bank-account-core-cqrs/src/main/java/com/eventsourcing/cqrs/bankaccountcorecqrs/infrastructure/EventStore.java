package com.eventsourcing.cqrs.bankaccountcorecqrs.infrastructure;

import com.eventsourcing.cqrs.bankaccountcorecqrs.events.BaseEvent;

import java.util.List;

public interface EventStore {

    void saveEvents(String aggregateId, Iterable<BaseEvent> events, int expectedVersion);

    List<BaseEvent> getEvents(String aggregateId);
}
