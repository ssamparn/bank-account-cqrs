package com.eventsourcing.cqrs.bankaccountcommon.events;

import com.eventsourcing.cqrs.bankaccountcorecqrs.events.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class FundsDepositedEvent extends BaseEvent {
    private double amount;
}
