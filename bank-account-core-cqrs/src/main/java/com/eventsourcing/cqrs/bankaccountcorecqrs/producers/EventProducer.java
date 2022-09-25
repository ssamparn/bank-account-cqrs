package com.eventsourcing.cqrs.bankaccountcorecqrs.producers;

import com.eventsourcing.cqrs.bankaccountcorecqrs.events.BaseEvent;

public interface EventProducer {
    void produce(String topic, BaseEvent event);
}
