package com.eventsourcing.cqrs.bankaccountcommandapi.handlers;

import com.eventsourcing.cqrs.bankaccountcommandapi.domain.AccountAggregate;
import com.eventsourcing.cqrs.bankaccountcommandapi.web.api.commands.CloseAccountCommand;
import com.eventsourcing.cqrs.bankaccountcommandapi.web.api.commands.DepositFundsCommand;
import com.eventsourcing.cqrs.bankaccountcommandapi.web.api.commands.OpenAccountCommand;
import com.eventsourcing.cqrs.bankaccountcommandapi.web.api.commands.RestoreReadDatabaseCommand;
import com.eventsourcing.cqrs.bankaccountcommandapi.web.api.commands.WithdrawFundsCommand;
import com.eventsourcing.cqrs.bankaccountcorecqrs.handlers.EventSourcingHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountCommandHandler implements CommandHandler {

    private final EventSourcingHandler<AccountAggregate> eventSourcingHandler;

    @Override
    public void handle(OpenAccountCommand command) {
        var accountAggregate = new AccountAggregate(command);
        eventSourcingHandler.save(accountAggregate);
    }

    @Override
    public void handle(DepositFundsCommand command) {
        var accountAggregate = eventSourcingHandler.getByAggregateId(command.getId());
        accountAggregate.depositFunds(command.getAmount());
        eventSourcingHandler.save(accountAggregate);
    }

    @Override
    public void handle(WithdrawFundsCommand command) {
        var accountAggregate = eventSourcingHandler.getByAggregateId(command.getId());

        if (command.getAmount() > accountAggregate.getBalance()) {
            throw new IllegalStateException("Withdrawl declined, Insufficient funds");
        }

        accountAggregate.withdrawFunds(command.getAmount());
        eventSourcingHandler.save(accountAggregate);
    }

    @Override
    public void handle(CloseAccountCommand command) {
        var accountAggregate = eventSourcingHandler.getByAggregateId(command.getId());
        accountAggregate.closeAccount();
        eventSourcingHandler.save(accountAggregate);
    }

    @Override
    public void handle(RestoreReadDatabaseCommand command) {
        eventSourcingHandler.republishEvents();
    }
}
