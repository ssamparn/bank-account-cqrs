package com.eventsourcing.cqrs.bankaccountqueryapi.repository;

import com.eventsourcing.cqrs.bankaccountcorecqrs.domain.BaseEntity;
import com.eventsourcing.cqrs.bankaccountqueryapi.domain.BankAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountsRepository extends JpaRepository<BankAccountEntity, String> {
    Optional<BankAccountEntity> findByAccountHolder(String accountHolder);
    List<BaseEntity> findByBalanceGreaterThan(double balance);
    List<BaseEntity> findByBalanceLessThan(double balance);
}
