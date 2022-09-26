package com.eventsourcing.cqrs.bankaccountcommandapi.web.api.controllers;

import com.eventsourcing.cqrs.bankaccountcommandapi.web.api.commands.WithdrawFundsCommand;
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
public class WithdrawFundsRestController {

    private final CommandDispatcher commandDispatcher;

    @PutMapping("/withdraw-funds/{accountId}")
    public ResponseEntity<BaseResponse> withdrawFunds(@PathVariable String accountId,
                                                    @RequestBody WithdrawFundsCommand withdrawFundsCommand) {
        withdrawFundsCommand.setId(accountId);

        try {
            commandDispatcher.send(withdrawFundsCommand);
            return new ResponseEntity<>(new BaseResponse("Withdraw funds request completed successfully"), HttpStatus.OK);
        } catch (IllegalStateException | AggregateNotFoundException e) {
            log.warn("Client made a BAD REQUEST: {}", e.getMessage());
            return new ResponseEntity<>(new BaseResponse(e.toString()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("Error while processing request to withdraw funds from the account with accountId : {}, {}", accountId, e.getMessage());
            return new ResponseEntity<>(new BaseResponse(e.toString()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
