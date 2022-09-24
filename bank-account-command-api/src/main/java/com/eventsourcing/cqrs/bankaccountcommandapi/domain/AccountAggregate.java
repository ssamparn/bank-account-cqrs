package com.eventsourcing.cqrs.bankaccountcommandapi.domain;

import com.eventsourcing.cqrs.bankaccountcommandapi.web.api.commands.OpenAccountCommand;
import com.eventsourcing.cqrs.bankaccountcommon.events.AccountClosedEvent;
import com.eventsourcing.cqrs.bankaccountcommon.events.AccountOpenedEvent;
import com.eventsourcing.cqrs.bankaccountcommon.events.FundsDepositedEvent;
import com.eventsourcing.cqrs.bankaccountcommon.events.FundsWithdrawnEvent;
import com.eventsourcing.cqrs.bankaccountcorecqrs.domain.AggregateRoot;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
public class AccountAggregate extends AggregateRoot {

    private Boolean active;
    private double balance;

    public AccountAggregate(OpenAccountCommand command) {
        AccountOpenedEvent accountOpenedEvent = AccountOpenedEvent.builder()
                .id(command.getId())
                .accountHolder(command.getAccountHolder())
                .createdDate(new Date())
                .accountType(command.getAccountType())
                .openingBalance(command.getOpeningBalance())
                .build();
        raiseEvent(accountOpenedEvent);
    }

    public void apply(AccountOpenedEvent event) {
        this.id = event.getId();
        this.active = true;
        this.balance = event.getOpeningBalance();
    }

    public void depositFunds(double amount) {
        if (!this.active) {
            throw new IllegalStateException("Funds cannot be deposited into a closed account!!");
        }
        if (amount <= 0) {
            throw new IllegalStateException("The deposited amount must be greater than 0!");
        }

        FundsDepositedEvent fundsDepositedEvent = FundsDepositedEvent.builder()
                .id(this.id)
                .amount(amount)
                .build();
        raiseEvent(fundsDepositedEvent);
    }

    public void apply(FundsDepositedEvent event) {
        this.id = event.getId();
        this.balance += event.getAmount();
    }

    public void withdrawFunds(double amount) {
        if (!this.active) {
            throw new IllegalStateException("Funds cannot be withdrawn from a closed account!!");
        }

        FundsWithdrawnEvent fundsWithdrawnEvent = FundsWithdrawnEvent.builder()
                .id(this.id)
                .amount(amount)
                .build();
    }

    public void apply(FundsWithdrawnEvent event) {
        this.id = event.getId();
        this.balance -= event.getAmount();
    }

    public void closeAccount() {
        if (!this.active) {
            throw new IllegalStateException("The bank account has already been closed!!");
        }
        AccountClosedEvent accountClosedEvent = AccountClosedEvent.builder()
                .id(this.id)
                .build();

        raiseEvent(accountClosedEvent);
    }

    public void apply(AccountClosedEvent event) {
        this.id = event.getId();
        this.active = false;
    }
}
