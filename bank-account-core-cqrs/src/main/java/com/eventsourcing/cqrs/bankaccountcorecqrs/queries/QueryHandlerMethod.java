package com.eventsourcing.cqrs.bankaccountcorecqrs.queries;

import com.eventsourcing.cqrs.bankaccountcorecqrs.domain.BaseEntity;

import java.util.List;

@FunctionalInterface
public interface QueryHandlerMethod<T extends BaseQuery> {
    List<BaseEntity> handle(T query);
}
