package com.eventsourcing.cqrs.bankaccountcommandapi.web.api.controllers;

import com.eventsourcing.cqrs.bankaccountcommandapi.web.api.commands.OpenAccountCommand;
import com.eventsourcing.cqrs.bankaccountcommandapi.web.api.model.OpenAccountResponse;
import com.eventsourcing.cqrs.bankaccountcommon.dto.response.BaseResponse;
import com.eventsourcing.cqrs.bankaccountcorecqrs.infrastructure.CommandDispatcher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1")
@RequiredArgsConstructor
public class OpenAccountRestController {

    private final CommandDispatcher commandDispatcher;

    @PostMapping("/open-bank-account")
    public ResponseEntity<BaseResponse> openAccount(@RequestBody OpenAccountCommand openAccountCommand) {
        var id  = UUID.randomUUID().toString();
        openAccountCommand.setId(id);

        try {
            commandDispatcher.send(openAccountCommand);
            return new ResponseEntity<>(new OpenAccountResponse("Bank Account creation request completed successfully"), HttpStatus.CREATED);
        } catch (IllegalStateException e) {
            log.warn("Client made a BAD REQUEST: {}", e.getMessage());
            return new ResponseEntity<>(new BaseResponse(e.toString()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("Error while processing request to open a new bank account for id: {}, {}", id, e.getMessage());
            return new ResponseEntity<>(new OpenAccountResponse(e.toString()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
