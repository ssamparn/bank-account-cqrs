package com.eventsourcing.cqrs.bankaccountcommandapi.producers;

import com.eventsourcing.cqrs.bankaccountcorecqrs.events.BaseEvent;
import com.eventsourcing.cqrs.bankaccountcorecqrs.producers.EventProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountEventProducer implements EventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void produce(String topic, BaseEvent event) {
        this.kafkaTemplate.send(topic, event);
    }
}
