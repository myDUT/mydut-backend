package com.capstoneproject.mydut.kafka.consumer;

import com.capstoneproject.mydut.kafka.constant.BKafkaConstant;
import com.capstoneproject.mydut.kafka.payload.SendUserRegistrationNotificationCommand;
import com.capstoneproject.mydut.kafka.topic.EmailKafkaTopic;
import com.capstoneproject.mydut.service.UserEmailKafkaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * @author vndat00
 * @since 10/20/2024
 */

@Component
@Log4j2
@RequiredArgsConstructor
@KafkaListener(
        topics = {
                EmailKafkaTopic.COMMAND_EMAIL_TOPIC
        },
        containerFactory = BKafkaConstant.JSON_LISTENER_CONTAINER_FACTORY
)
public class UserEmailKafkaConsumer {
    private final ObjectMapper objectMapper;
    private final UserEmailKafkaService userEmailKafkaService;

    @KafkaHandler
    public void sendEmailForUserRegistrationNotification(@Payload SendUserRegistrationNotificationCommand payload) {
        try {
            userEmailKafkaService.sendEmailForUserRegistrationNotification(payload);
        } catch (Exception e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }
    @KafkaHandler(isDefault = true)
    public void unrecognizedMessage(@Payload Object unrecognizedMessage) {
        try {
            log.warn("Unrecognized message found with type {} and content {}", unrecognizedMessage.getClass().getSimpleName(), objectMapper.writeValueAsString(unrecognizedMessage));
        } catch (JsonProcessingException e) {
            log.error("Error when write message to string. {}", e.getMessage());
            log.warn("Unrecognized malformed message found with type {}", unrecognizedMessage.getClass().getSimpleName());
        }
    }
}
