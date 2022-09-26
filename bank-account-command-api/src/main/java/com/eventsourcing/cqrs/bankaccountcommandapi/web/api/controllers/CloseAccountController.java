package com.eventsourcing.cqrs.bankaccountcommandapi.web.api.controllers;

import com.eventsourcing.cqrs.bankaccountcommandapi.web.api.commands.CloseAccountCommand;
import com.eventsourcing.cqrs.bankaccountcommon.dto.response.BaseResponse;
import com.eventsourcing.cqrs.bankaccountcorecqrs.exceptions.AggregateNotFoundException;
import com.eventsourcing.cqrs.bankaccountcorecqrs.infrastructure.CommandDispatcher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1")
@RequiredArgsConstructor
public class CloseAccountController {

    private final CommandDispatcher commandDispatcher;

    @DeleteMapping("/close-bank-account/{accountId}")
    public ResponseEntity<BaseResponse> closeAccount(@PathVariable String accountId) {
        try {
            commandDispatcher.send(new CloseAccountCommand(accountId));
            return new ResponseEntity<>(new BaseResponse("Bank account closure request completed successfully"), HttpStatus.OK);
        } catch (IllegalStateException | AggregateNotFoundException e) {
            log.warn("Client made a BAD REQUEST: {}", e.getMessage());
            return new ResponseEntity<>(new BaseResponse(e.toString()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("Error while processing request to close bank account with accountId : {}, {}", accountId, e.getMessage());
            return new ResponseEntity<>(new BaseResponse(e.toString()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
