package com.eventsourcing.cqrs.bankaccountqueryapi.config;

import com.eventsourcing.cqrs.bankaccountcorecqrs.infrastructure.QueryDispatcher;
import com.eventsourcing.cqrs.bankaccountqueryapi.handlers.QueryHandler;
import com.eventsourcing.cqrs.bankaccountqueryapi.web.api.queries.FindAccountByHolderQuery;
import com.eventsourcing.cqrs.bankaccountqueryapi.web.api.queries.FindAccountByIdQuery;
import com.eventsourcing.cqrs.bankaccountqueryapi.web.api.queries.FindAccountsWithBalanceQuery;
import com.eventsourcing.cqrs.bankaccountqueryapi.web.api.queries.FindAllAccountsQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@RequiredArgsConstructor
public class BankAccountQueryConfig {

    private final QueryDispatcher queryDispatcher;
    private final QueryHandler queryHandler;

    @PostConstruct
    public void registerHandlers() {
        queryDispatcher.registerHandler(FindAllAccountsQuery.class, queryHandler::handle);
        queryDispatcher.registerHandler(FindAccountByIdQuery.class, queryHandler::handle);
        queryDispatcher.registerHandler(FindAccountByHolderQuery.class, queryHandler::handle);
        queryDispatcher.registerHandler(FindAccountsWithBalanceQuery.class, queryHandler::handle);
    }
}
