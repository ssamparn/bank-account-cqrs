package com.eventsourcing.cqrs.bankaccountqueryapi.web.api.queries;

import com.eventsourcing.cqrs.bankaccountcorecqrs.queries.BaseQuery;
import com.eventsourcing.cqrs.bankaccountqueryapi.web.api.model.EqualityType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FindAccountsWithBalanceQuery extends BaseQuery {
    private EqualityType equalityType;
    private double balance;
}
