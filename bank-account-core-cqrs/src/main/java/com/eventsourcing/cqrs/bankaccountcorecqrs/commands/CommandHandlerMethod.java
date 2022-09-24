package com.eventsourcing.cqrs.bankaccountcorecqrs.commands;

@FunctionalInterface
public interface CommandHandlerMethod<T extends BaseCommand> {
    void handle(T command);
}
