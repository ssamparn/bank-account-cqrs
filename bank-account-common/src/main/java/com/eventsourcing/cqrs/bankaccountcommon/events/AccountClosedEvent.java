package com.eventsourcing.cqrs.bankaccountcommon.events;

import com.eventsourcing.cqrs.bankaccountcorecqrs.events.BaseEvent;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
public class AccountClosedEvent extends BaseEvent {

}
