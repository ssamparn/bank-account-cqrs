package com.eventsourcing.cqrs.bankaccountqueryapi.handlers;

import com.eventsourcing.cqrs.bankaccountcommon.events.AccountClosedEvent;
import com.eventsourcing.cqrs.bankaccountcommon.events.AccountOpenedEvent;
import com.eventsourcing.cqrs.bankaccountcommon.events.FundsDepositedEvent;
import com.eventsourcing.cqrs.bankaccountcommon.events.FundsWithdrawnEvent;

public interface EventHandler {
    void on(AccountOpenedEvent event);
    void on(FundsDepositedEvent event);
    void on(FundsWithdrawnEvent event);
    void on(AccountClosedEvent event);
}
