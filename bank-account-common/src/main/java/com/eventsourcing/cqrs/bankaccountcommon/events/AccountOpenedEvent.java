package com.eventsourcing.cqrs.bankaccountcommon.events;

import com.eventsourcing.cqrs.bankaccountcommon.dto.AccountType;
import com.eventsourcing.cqrs.bankaccountcorecqrs.events.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class AccountOpenedEvent extends BaseEvent {
    private String accountHolder;
    private AccountType accountType;
    private Date createdDate;
    private double openingBalance;
}
