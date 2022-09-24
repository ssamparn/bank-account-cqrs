package com.eventsourcing.cqrs.bankaccountcorecqrs.infrastructure;

import com.eventsourcing.cqrs.bankaccountcorecqrs.commands.BaseCommand;
import com.eventsourcing.cqrs.bankaccountcorecqrs.commands.CommandHandlerMethod;

public interface CommandDispatcher {
    <T extends BaseCommand> void registerHandler(Class<T> type, CommandHandlerMethod<T> handler);
    void send(BaseCommand command);
}
