package com.pmss0168.notificationservice.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.DltStrategy;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.retry.RetryException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EventConsumer {

    @RetryableTopic(
            attempts = "4",  //3 retry topic + 1 dlt topic
            backoff = @Backoff(delay = 1000, multiplier = 2),
            autoCreateTopics = "true",
            dltStrategy = DltStrategy.FAIL_ON_ERROR,
            include = {RetryException.class, RuntimeException.class}
    )
    @KafkaListener(topics = "test", containerFactory = "kafkaListenerContainerFactory")
    public void listen(String message) {
        log.info("Message received: {}", message);
        throw new RuntimeException("Error message");
    }

    @DltHandler
    public void dltHandler(@Payload String message) {
        log.info("DLT message received: {}", message);
    }
}
