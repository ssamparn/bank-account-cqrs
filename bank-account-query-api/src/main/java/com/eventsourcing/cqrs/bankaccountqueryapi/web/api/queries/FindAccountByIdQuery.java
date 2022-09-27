package com.eventsourcing.cqrs.bankaccountqueryapi.web.api.queries;

import com.eventsourcing.cqrs.bankaccountcorecqrs.queries.BaseQuery;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FindAccountByIdQuery extends BaseQuery {
    private String accountId;
}
