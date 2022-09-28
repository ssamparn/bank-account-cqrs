package com.eventsourcing.cqrs.bankaccountcommandapi.infrastructure;

import com.eventsourcing.cqrs.bankaccountcommandapi.domain.AccountAggregate;
import com.eventsourcing.cqrs.bankaccountcommandapi.repository.EventStoreRepository;
import com.eventsourcing.cqrs.bankaccountcorecqrs.events.BaseEvent;
import com.eventsourcing.cqrs.bankaccountcorecqrs.events.EventModel;
import com.eventsourcing.cqrs.bankaccountcorecqrs.exceptions.AggregateNotFoundException;
import com.eventsourcing.cqrs.bankaccountcorecqrs.exceptions.ConcurrencyException;
import com.eventsourcing.cqrs.bankaccountcorecqrs.infrastructure.EventStore;
import com.eventsourcing.cqrs.bankaccountcorecqrs.producers.EventProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountEventStore implements EventStore {

    private final EventStoreRepository eventStoreRepository;
    private final EventProducer eventProducer;

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
            if (!persistedEvent.getId().isEmpty()) {
                eventProducer.produce(event.getClass().getSimpleName(), event);
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

    @Override
    public List<String> getAggregateIds() {
        List<EventModel> eventModels = eventStoreRepository.findAll();

        if (eventModels.isEmpty()) {
            throw new IllegalStateException("Could not retrieve event models from event store!");
        }
        return eventModels.stream()
                .map(EventModel::getAggregateIdentifier)
                .distinct()
                .collect(Collectors.toList());
    }
}
