package com.pmss0168.notificationservice.event;

import com.pmss0168.commonservice.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.DltStrategy;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.retry.RetryException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class EventConsumer {
    @Autowired
    private MailService mailService;

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

    @KafkaListener(topics = "sendMail", containerFactory = "kafkaListenerContainerFactory")
    public void listenSendEmail(String message) {
        log.info("Email message received: {}", message);
        String template = "<div>\n" +
                "    <h1>Welcome, %s!</h1>\n" +
                "    <p>Thank you for joining us. We're excited to have you on board.</p>\n" +
                "    <p>Your username is: <strong>%s</strong></p>\n" +
                "</div>";
        String filledTemplate = String.format(template,"Sangs",message);
        mailService.sendEmail(message, "Thanks for support", filledTemplate, true, null);
    }

    @KafkaListener(topics = "sendTemplateMail", containerFactory = "kafkaListenerContainerFactory")
    public void listenSendTemplateEmail(String message) {
        log.info("Email message received: {}", message);
        Map<String, Object> placeHolders = new HashMap<>();
        placeHolders.put("name", message);
        mailService.sendEmailWithTemplate(message, "Merry Christmas", "mail-template.ftl", placeHolders, null);
    }

    @DltHandler
    public void dltHandler(@Payload String message) {
        log.info("DLT message received: {}", message);
    }
}
