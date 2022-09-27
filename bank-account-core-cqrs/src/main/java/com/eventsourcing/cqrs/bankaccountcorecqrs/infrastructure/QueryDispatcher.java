package com.eventsourcing.cqrs.bankaccountcorecqrs.infrastructure;

import com.eventsourcing.cqrs.bankaccountcorecqrs.domain.BaseEntity;
import com.eventsourcing.cqrs.bankaccountcorecqrs.queries.BaseQuery;
import com.eventsourcing.cqrs.bankaccountcorecqrs.queries.QueryHandlerMethod;

import java.util.List;

public interface QueryDispatcher {
    <T extends BaseQuery> void registerHandler(Class<T> type, QueryHandlerMethod<T> handler);
    <U extends BaseEntity> List<U> send(BaseQuery query);
}
