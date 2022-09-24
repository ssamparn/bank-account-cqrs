package com.eventsourcing.cqrs.bankaccountcommon.events;

import com.eventsourcing.cqrs.bankaccountcorecqrs.events.BaseEvent;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class AccountClosedEvent extends BaseEvent {

}
