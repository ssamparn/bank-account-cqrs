package com.eventsourcing.cqrs.bankaccountqueryapi.infrastructure;

import com.eventsourcing.cqrs.bankaccountcorecqrs.domain.BaseEntity;
import com.eventsourcing.cqrs.bankaccountcorecqrs.infrastructure.QueryDispatcher;
import com.eventsourcing.cqrs.bankaccountcorecqrs.queries.BaseQuery;
import com.eventsourcing.cqrs.bankaccountcorecqrs.queries.QueryHandlerMethod;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class AccountQueryDispatcher implements QueryDispatcher {

    private final Map<Class<? extends BaseQuery>, List<QueryHandlerMethod>> routes = new HashMap<>();

    @Override
    public <T extends BaseQuery> void registerHandler(Class<T> type, QueryHandlerMethod<T> handler) {
        var queryHandlerMethods = routes.computeIfAbsent(type, c -> new LinkedList<>());
        queryHandlerMethods.add(handler);
    }

    @Override
    public <U extends BaseEntity> List<U> send(BaseQuery query) {
        var queryHandlerMethods = routes.get(query.getClass());

        if (queryHandlerMethods == null || queryHandlerMethods.size() == 0) {
            throw new RuntimeException("No Query Handler was registered");
        }

        if (queryHandlerMethods.size() > 1) {
            throw new RuntimeException("Cannot send query to more than one handler!");
        }
        return queryHandlerMethods.get(0).handle(query);
    }
}
