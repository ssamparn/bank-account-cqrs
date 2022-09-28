package com.eventsourcing.cqrs.bankaccountcommandapi.web.api.controllers;

import com.eventsourcing.cqrs.bankaccountcommandapi.web.api.commands.RestoreReadDatabaseCommand;
import com.eventsourcing.cqrs.bankaccountcommon.dto.response.BaseResponse;
import com.eventsourcing.cqrs.bankaccountcorecqrs.exceptions.AggregateNotFoundException;
import com.eventsourcing.cqrs.bankaccountcorecqrs.infrastructure.CommandDispatcher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1")
@RequiredArgsConstructor
public class RestoreReadDataBaseController {

    private final CommandDispatcher commandDispatcher;

    @PostMapping("/restore-read-database")
    public ResponseEntity<BaseResponse> restoreReadDatabase() {
        try {
            commandDispatcher.send(new RestoreReadDatabaseCommand());
            return new ResponseEntity<>(new BaseResponse("Restore read database request completed successfully"), HttpStatus.OK);
        } catch (IllegalStateException | AggregateNotFoundException e) {
            log.warn("Client made a BAD REQUEST: {}", e.getMessage());
            return new ResponseEntity<>(new BaseResponse(e.toString()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("Error while processing request to restore read database: {}", e.getMessage());
            return new ResponseEntity<>(new BaseResponse(e.toString()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
