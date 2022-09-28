package com.eventsourcing.cqrs.bankaccountcommandapi.config;

import com.eventsourcing.cqrs.bankaccountcommandapi.handlers.CommandHandler;
import com.eventsourcing.cqrs.bankaccountcommandapi.web.api.commands.CloseAccountCommand;
import com.eventsourcing.cqrs.bankaccountcommandapi.web.api.commands.DepositFundsCommand;
import com.eventsourcing.cqrs.bankaccountcommandapi.web.api.commands.OpenAccountCommand;
import com.eventsourcing.cqrs.bankaccountcommandapi.web.api.commands.RestoreReadDatabaseCommand;
import com.eventsourcing.cqrs.bankaccountcommandapi.web.api.commands.WithdrawFundsCommand;
import com.eventsourcing.cqrs.bankaccountcorecqrs.infrastructure.CommandDispatcher;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@RequiredArgsConstructor
public class BankAccountCommandConfig {

    private final CommandDispatcher commandDispatcher;
    private final CommandHandler commandHandler;

    @PostConstruct
    public void registerHandlers() {
        commandDispatcher.registerHandler(OpenAccountCommand.class, commandHandler::handle);
        commandDispatcher.registerHandler(DepositFundsCommand.class, commandHandler::handle);
        commandDispatcher.registerHandler(WithdrawFundsCommand.class, commandHandler::handle);
        commandDispatcher.registerHandler(CloseAccountCommand.class, commandHandler::handle);
        commandDispatcher.registerHandler(RestoreReadDatabaseCommand.class, commandHandler::handle);
    }
}
