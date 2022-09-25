package com.eventsourcing.cqrs.bankaccountqueryapi.consumers;

import com.eventsourcing.cqrs.bankaccountcommon.events.AccountClosedEvent;
import com.eventsourcing.cqrs.bankaccountcommon.events.AccountOpenedEvent;
import com.eventsourcing.cqrs.bankaccountcommon.events.FundsDepositedEvent;
import com.eventsourcing.cqrs.bankaccountcommon.events.FundsWithdrawnEvent;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;

public interface EventConsumer {
    void consume(@Payload AccountOpenedEvent event, Acknowledgment ack);
    void consume(@Payload FundsDepositedEvent event, Acknowledgment ack);
    void consume(@Payload FundsWithdrawnEvent event, Acknowledgment ack);
    void consume(@Payload AccountClosedEvent event, Acknowledgment ack);
}
