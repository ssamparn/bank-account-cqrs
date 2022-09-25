package com.eventsourcing.cqrs.bankaccountcommandapi.handlers;

import com.eventsourcing.cqrs.bankaccountcommandapi.web.api.commands.CloseAccountCommand;
import com.eventsourcing.cqrs.bankaccountcommandapi.web.api.commands.DepositFundsCommand;
import com.eventsourcing.cqrs.bankaccountcommandapi.web.api.commands.OpenAccountCommand;
import com.eventsourcing.cqrs.bankaccountcommandapi.web.api.commands.WithdrawFundsCommand;

public interface CommandHandler {
    void handle(OpenAccountCommand command);
    void handle(DepositFundsCommand command);
    void handle(WithdrawFundsCommand command);
    void handle(CloseAccountCommand command);
}
