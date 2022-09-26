package com.eventsourcing.cqrs.bankaccountcommandapi.web.api.controllers;

import com.eventsourcing.cqrs.bankaccountcommandapi.web.api.commands.DepositFundsCommand;
import com.eventsourcing.cqrs.bankaccountcommon.dto.response.BaseResponse;
import com.eventsourcing.cqrs.bankaccountcorecqrs.exceptions.AggregateNotFoundException;
import com.eventsourcing.cqrs.bankaccountcorecqrs.infrastructure.CommandDispatcher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1")
@RequiredArgsConstructor
public class DepositFundsRestController {

    private final CommandDispatcher commandDispatcher;

    @PutMapping("/deposit-funds/{accountId}")
    public ResponseEntity<BaseResponse> depositFunds(@PathVariable String accountId,
                                                    @RequestBody DepositFundsCommand depositFundsCommand) {
        depositFundsCommand.setId(accountId);

        try {
            commandDispatcher.send(depositFundsCommand);
            return new ResponseEntity<>(new BaseResponse("Deposit funds request completed successfully"), HttpStatus.OK);
        } catch (IllegalStateException | AggregateNotFoundException e) {
            log.warn("Client made a BAD REQUEST: {}", e.getMessage());
            return new ResponseEntity<>(new BaseResponse(e.toString()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("Error while processing request to deposit funds in the account with accountId : {}, {}", accountId, e.getMessage());
            return new ResponseEntity<>(new BaseResponse(e.toString()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
