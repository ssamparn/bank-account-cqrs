package com.eventsourcing.cqrs.bankaccountcorecqrs.commands;

import com.eventsourcing.cqrs.bankaccountcorecqrs.messages.Message;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public abstract class BaseCommand extends Message {

    public BaseCommand(String id) {
        super(id);
    }

}
