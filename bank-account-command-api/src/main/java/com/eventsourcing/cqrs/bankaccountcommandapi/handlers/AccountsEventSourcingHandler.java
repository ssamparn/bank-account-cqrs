package com.eventsourcing.cqrs.bankaccountcommandapi.handlers;

import com.eventsourcing.cqrs.bankaccountcommandapi.domain.AccountAggregate;
import com.eventsourcing.cqrs.bankaccountcorecqrs.domain.AggregateRoot;
import com.eventsourcing.cqrs.bankaccountcorecqrs.events.BaseEvent;
import com.eventsourcing.cqrs.bankaccountcorecqrs.handlers.EventSourcingHandler;
import com.eventsourcing.cqrs.bankaccountcorecqrs.infrastructure.EventStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@Service
@RequiredArgsConstructor
public class AccountsEventSourcingHandler implements EventSourcingHandler<AccountAggregate> {

    private final EventStore eventStore;

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
}
