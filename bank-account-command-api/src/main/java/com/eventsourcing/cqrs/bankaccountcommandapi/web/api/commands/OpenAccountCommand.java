package com.eventsourcing.cqrs.bankaccountcommandapi.web.api.commands;

import com.eventsourcing.cqrs.bankaccountcommon.dto.AccountType;
import com.eventsourcing.cqrs.bankaccountcorecqrs.commands.BaseCommand;
import lombok.Data;

@Data
public class OpenAccountCommand extends BaseCommand {
    private String accountHolder;
    private AccountType accountType;
    private double openingBalance;
}
