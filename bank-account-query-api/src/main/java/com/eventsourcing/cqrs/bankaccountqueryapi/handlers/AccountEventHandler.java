package com.eventsourcing.cqrs.bankaccountqueryapi.handlers;

import com.eventsourcing.cqrs.bankaccountcommon.events.AccountClosedEvent;
import com.eventsourcing.cqrs.bankaccountcommon.events.AccountOpenedEvent;
import com.eventsourcing.cqrs.bankaccountcommon.events.FundsDepositedEvent;
import com.eventsourcing.cqrs.bankaccountcommon.events.FundsWithdrawnEvent;
import com.eventsourcing.cqrs.bankaccountqueryapi.domain.BankAccountEntity;
import com.eventsourcing.cqrs.bankaccountqueryapi.repository.AccountsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountEventHandler implements EventHandler {

    private final AccountsRepository accountsRepository;

    @Override
    public void on(AccountOpenedEvent event) {

        var bankAccountEntity = BankAccountEntity.builder()
                .id(event.getId())
                .accountHolder(event.getAccountHolder())
                .creationDate(event.getCreatedDate())
                .accountType(event.getAccountType())
                .balance(event.getOpeningBalance())
                .build();

        accountsRepository.save(bankAccountEntity);
    }

    @Override
    public void on(FundsDepositedEvent event) {

        var bankAccountEntityOptional = accountsRepository.findById(event.getId());

        if (bankAccountEntityOptional.isEmpty()) {
            return;
        }
        var currentBalance = bankAccountEntityOptional.get().getBalance();
        var latestBalance = currentBalance + event.getAmount();
        bankAccountEntityOptional.get().setBalance(latestBalance);

        accountsRepository.save(bankAccountEntityOptional.get());
    }

    @Override
    public void on(FundsWithdrawnEvent event) {
        var bankAccountEntityOptional = accountsRepository.findById(event.getId());

        if (bankAccountEntityOptional.isEmpty()) {
            return;
        }
        var currentBalance = bankAccountEntityOptional.get().getBalance();
        var latestBalance = currentBalance - event.getAmount();
        bankAccountEntityOptional.get().setBalance(latestBalance);

        accountsRepository.save(bankAccountEntityOptional.get());
    }

    @Override
    public void on(AccountClosedEvent event) {
        accountsRepository.deleteById(event.getId());
    }
}
