package com.eventsourcing.cqrs.bankaccountcommandapi.handlers;

import com.eventsourcing.cqrs.bankaccountcommandapi.domain.AccountAggregate;
import com.eventsourcing.cqrs.bankaccountcorecqrs.domain.AggregateRoot;
import com.eventsourcing.cqrs.bankaccountcorecqrs.events.BaseEvent;
import com.eventsourcing.cqrs.bankaccountcorecqrs.handlers.EventSourcingHandler;
import com.eventsourcing.cqrs.bankaccountcorecqrs.infrastructure.EventStore;
import com.eventsourcing.cqrs.bankaccountcorecqrs.producers.EventProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountsEventSourcingHandler implements EventSourcingHandler<AccountAggregate> {

    private final EventStore eventStore;
    private final EventProducer eventProducer;

    @Override
    public void save(AggregateRoot aggregate) {
        eventStore.saveEvents(aggregate.getId(), aggregate.getUncommittedChanges(), aggregate.getVersion());
        aggregate.markChangesAsCommitted();
    }

    @Override
    public AccountAggregate getByAggregateId(String aggregateId) {
        var aggregate = new AccountAggregate();
        var events = eventStore.getEvents(aggregateId);

        if (events != null && !events.isEmpty()) {
            aggregate.replyEvents(events);
            var latestVersion = events.stream().map(BaseEvent::getVersion).max(Comparator.naturalOrder());
            aggregate.setVersion(latestVersion.get());
        }
        return aggregate;
    }

    @Override
    public void republishEvents() {
        var aggregateIds = eventStore.getAggregateIds();
        for (var aggregateId : aggregateIds) {
            AccountAggregate accountAggregate = getByAggregateId(aggregateId);

            if (accountAggregate == null || !accountAggregate.isActive()) {
                continue;
            }

            List<BaseEvent> events = eventStore.getEvents(aggregateId);
            for (var event : events) {
                eventProducer.produce(event.getClass().getSimpleName(), event);
            }
        }
    }
}
