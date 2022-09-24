package com.eventsourcing.cqrs.bankaccountcommandapi.infrastructure;

import com.eventsourcing.cqrs.bankaccountcommandapi.domain.AccountAggregate;
import com.eventsourcing.cqrs.bankaccountcommandapi.domain.EventStoreRepository;
import com.eventsourcing.cqrs.bankaccountcorecqrs.events.BaseEvent;
import com.eventsourcing.cqrs.bankaccountcorecqrs.events.EventModel;
import com.eventsourcing.cqrs.bankaccountcorecqrs.exceptions.AggregateNotFoundException;
import com.eventsourcing.cqrs.bankaccountcorecqrs.exceptions.ConcurrencyException;
import com.eventsourcing.cqrs.bankaccountcorecqrs.infrastructure.EventStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountEventStore implements EventStore {

    private final EventStoreRepository eventStoreRepository;

    @Override
    public void saveEvents(String aggregateId, Iterable<BaseEvent> events, int expectedVersion) {
        var eventStream = eventStoreRepository.findByAggregateIdentifier(aggregateId);

        if (expectedVersion != -1 && eventStream.get(eventStream.size() - 1).getVersion() != expectedVersion) {
            throw new ConcurrencyException("concurrencyException");
        }

        var version = expectedVersion;

        for (var event : events) {
            version ++;
            event.setVersion(version);

            var eventModel = EventModel.builder()
                    .timeStamp(new Date())
                    .aggregateIdentifier(aggregateId)
                    .aggregateType(AccountAggregate.class.getTypeName())
                    .version(version)
                    .eventType(event.getClass().getTypeName())
                    .eventData(event)
                    .build();

            var persistedEvent = eventStoreRepository.save(eventModel);
            if (persistedEvent != null) {
                // TODO: produce event to Kafka
            }
        }
    }

    @Override
    public List<BaseEvent> getEvents(String aggregateId) {
        var eventStream = eventStoreRepository.findByAggregateIdentifier(aggregateId);

        if (eventStream == null && eventStream.isEmpty()) {
            throw new AggregateNotFoundException("aggregateNotFoundException: Incorrect accountId provided");
        }

        return eventStream.stream()
                .map(EventModel::getEventData)
                .collect(Collectors.toList());
    }
}