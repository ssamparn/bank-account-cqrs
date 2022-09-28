package com.eventsourcing.cqrs.bankaccountqueryapi.web.api.controllers;

import com.eventsourcing.cqrs.bankaccountcorecqrs.infrastructure.QueryDispatcher;
import com.eventsourcing.cqrs.bankaccountqueryapi.domain.BankAccountEntity;
import com.eventsourcing.cqrs.bankaccountqueryapi.web.api.model.AccountLookUpResponse;
import com.eventsourcing.cqrs.bankaccountqueryapi.web.api.model.EqualityType;
import com.eventsourcing.cqrs.bankaccountqueryapi.web.api.queries.FindAccountByHolderQuery;
import com.eventsourcing.cqrs.bankaccountqueryapi.web.api.queries.FindAccountByIdQuery;
import com.eventsourcing.cqrs.bankaccountqueryapi.web.api.queries.FindAccountsWithBalanceQuery;
import com.eventsourcing.cqrs.bankaccountqueryapi.web.api.queries.FindAllAccountsQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1")
public class AccountLookUpRestController {

    private final QueryDispatcher queryDispatcher;

    @GetMapping("/get-all-accounts")
    public ResponseEntity<AccountLookUpResponse> getAllAccounts() {
        try {
            List<BankAccountEntity> accountEntities = queryDispatcher.send(new FindAllAccountsQuery());
            if (accountEntities == null || accountEntities.size() == 0) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            var response = AccountLookUpResponse.builder()
                    .accounts(accountEntities)
                    .message("Successfully returned " + accountEntities.size() + " bank account(s)!")
                    .build();

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            var safeErrorMessage = "Failed to complete get all accounts request!";
            return new ResponseEntity<>(new AccountLookUpResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get-account-by-id/{accountId}")
    public ResponseEntity<AccountLookUpResponse> getAccountById(@PathVariable(name = "accountId") String accountId) {
        try {
            List<BankAccountEntity> accountEntities = queryDispatcher.send(new FindAccountByIdQuery(accountId));
            if (accountEntities == null || accountEntities.size() == 0) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            var response = AccountLookUpResponse.builder()
                    .accounts(accountEntities)
                    .message("Successfully returned bank account!")
                    .build();

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            var safeErrorMessage = "Failed to complete get account by id request!";
            return new ResponseEntity<>(new AccountLookUpResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get-account-by-account-holder/{accountHolder}")
    public ResponseEntity<AccountLookUpResponse> getAccountByAccountHolder(@PathVariable(name = "accountHolder") String accountHolder) {
        try {
            List<BankAccountEntity> accountEntities = queryDispatcher.send(new FindAccountByHolderQuery(accountHolder));
            if (accountEntities == null || accountEntities.size() == 0) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            var response = AccountLookUpResponse.builder()
                    .accounts(accountEntities)
                    .message("Successfully returned bank account!")
                    .build();

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            var safeErrorMessage = "Failed to complete get account by account holder request!";
            return new ResponseEntity<>(new AccountLookUpResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get-account-with-balance/{equalityType}/{balance}")
    public ResponseEntity<AccountLookUpResponse> getAccountWithBalance(@PathVariable(name = "equalityType") EqualityType equalityType,
                                                                       @PathVariable(name = "balance") double balance) {
        try {
            List<BankAccountEntity> accountEntities = queryDispatcher.send(new FindAccountsWithBalanceQuery(equalityType, balance));
            if (accountEntities == null || accountEntities.size() == 0) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            var response = AccountLookUpResponse.builder()
                    .accounts(accountEntities)
                    .message("Successfully returned " + accountEntities.size() + " bank account(s)!")
                    .build();

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            var safeErrorMessage = "Failed to complete get accounts with balance request!";
            return new ResponseEntity<>(new AccountLookUpResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
