package com.eventsourcing.cqrs.bankaccountcommandapi.infrastructure;

import com.eventsourcing.cqrs.bankaccountcorecqrs.commands.BaseCommand;
import com.eventsourcing.cqrs.bankaccountcorecqrs.commands.CommandHandlerMethod;
import com.eventsourcing.cqrs.bankaccountcorecqrs.infrastructure.CommandDispatcher;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class AccountCommandDispatcher implements CommandDispatcher {

    private final Map<Class<? extends BaseCommand>, List<CommandHandlerMethod>> routes = new HashMap<>();

    @Override
    public <T extends BaseCommand> void registerHandler(Class<T> type, CommandHandlerMethod<T> handler) {
        var commandHandlerMethods = routes.computeIfAbsent(type, c -> new LinkedList<>());
        commandHandlerMethods.add(handler);
    }

    @Override
    public void send(BaseCommand command) {
        var commandHandlerMethods = routes.get(command.getClass());

        if (commandHandlerMethods == null || commandHandlerMethods.size() == 0) {
            throw new RuntimeException("No Command Handler was registered");
        }

        if (commandHandlerMethods.size() > 1) {
            throw new RuntimeException("Cannot send command to more than one handler!");
        }

        commandHandlerMethods.get(0).handle(command);
    }
}
