package com.eventsourcing.cqrs.bankaccountcommandapi.web.api.commands;

import com.eventsourcing.cqrs.bankaccountcorecqrs.commands.BaseCommand;

public class CloseAccountCommand extends BaseCommand {
    public CloseAccountCommand(String id) {
        super(id);
    }
}
