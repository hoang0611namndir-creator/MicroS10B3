package com.pharmacy.service.event;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderEventProducer {

    private static final String TOPIC = "medicine-stock-events";

    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

    public void sendOrderEvent(OrderEvent event) {

        String key = event.getMedicineId();

        kafkaTemplate.send(
                TOPIC,
                key,
                event
        );
    }
}