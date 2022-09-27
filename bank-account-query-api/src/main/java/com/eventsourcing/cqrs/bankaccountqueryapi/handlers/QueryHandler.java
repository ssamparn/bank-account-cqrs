package com.eventsourcing.cqrs.bankaccountqueryapi.handlers;

import com.eventsourcing.cqrs.bankaccountcorecqrs.domain.BaseEntity;
import com.eventsourcing.cqrs.bankaccountqueryapi.web.api.queries.FindAccountByHolderQuery;
import com.eventsourcing.cqrs.bankaccountqueryapi.web.api.queries.FindAccountByIdQuery;
import com.eventsourcing.cqrs.bankaccountqueryapi.web.api.queries.FindAccountsWithBalanceQuery;
import com.eventsourcing.cqrs.bankaccountqueryapi.web.api.queries.FindAllAccountsQuery;

import java.util.List;

public interface QueryHandler {
    List<BaseEntity> handle(FindAllAccountsQuery query);
    List<BaseEntity> handle(FindAccountByIdQuery query);
    List<BaseEntity> handle(FindAccountByHolderQuery query);
    List<BaseEntity> handle(FindAccountsWithBalanceQuery query);
}
