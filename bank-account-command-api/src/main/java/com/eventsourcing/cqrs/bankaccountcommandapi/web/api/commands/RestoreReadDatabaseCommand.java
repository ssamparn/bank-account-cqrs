package com.eventsourcing.cqrs.bankaccountcommandapi.web.api.commands;

import com.eventsourcing.cqrs.bankaccountcorecqrs.commands.BaseCommand;
import lombok.Data;

@Data
public class RestoreReadDatabaseCommand extends BaseCommand {
}
