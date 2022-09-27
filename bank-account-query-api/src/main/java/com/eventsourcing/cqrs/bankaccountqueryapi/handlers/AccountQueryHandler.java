package com.eventsourcing.cqrs.bankaccountqueryapi.handlers;

import com.eventsourcing.cqrs.bankaccountcorecqrs.domain.BaseEntity;
import com.eventsourcing.cqrs.bankaccountqueryapi.domain.BankAccountEntity;
import com.eventsourcing.cqrs.bankaccountqueryapi.repository.AccountsRepository;
import com.eventsourcing.cqrs.bankaccountqueryapi.web.api.model.EqualityType;
import com.eventsourcing.cqrs.bankaccountqueryapi.web.api.queries.FindAccountByHolderQuery;
import com.eventsourcing.cqrs.bankaccountqueryapi.web.api.queries.FindAccountByIdQuery;
import com.eventsourcing.cqrs.bankaccountqueryapi.web.api.queries.FindAccountsWithBalanceQuery;
import com.eventsourcing.cqrs.bankaccountqueryapi.web.api.queries.FindAllAccountsQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountQueryHandler implements QueryHandler {

    private final AccountsRepository accountsRepository;

    @Override
    public List<BaseEntity> handle(FindAllAccountsQuery query) {
        List<BankAccountEntity> bankAccountEntities = accountsRepository.findAll();

        List<BaseEntity> bankAccountsList = new ArrayList<>(bankAccountEntities);

        return bankAccountsList;
    }

    @Override
    public List<BaseEntity> handle(FindAccountByIdQuery query) {
        Optional<BankAccountEntity> bankAccountEntityOptional = accountsRepository.findById(query.getAccountId());

        if (bankAccountEntityOptional.isEmpty()) {
            return null;
        }

        List<BaseEntity> bankAccountsList = new ArrayList<>();
        bankAccountsList.add(bankAccountEntityOptional.get());

        return bankAccountsList;
    }

    @Override
    public List<BaseEntity> handle(FindAccountByHolderQuery query) {
        Optional<BankAccountEntity> bankAccountEntityOptional = accountsRepository.findByAccountHolder(query.getAccountHolder());

        if (bankAccountEntityOptional.isEmpty()) {
            return null;
        }

        List<BaseEntity> bankAccountsList = new ArrayList<>();
        bankAccountsList.add(bankAccountEntityOptional.get());

        return bankAccountsList;
    }

    @Override
    public List<BaseEntity> handle(FindAccountsWithBalanceQuery query) {
        return query.getEqualityType() == EqualityType.GREATER_THAN ?
            accountsRepository.findByBalanceGreaterThan(query.getBalance())
            :
            accountsRepository.findByBalanceLessThan(query.getBalance());
    }
}
