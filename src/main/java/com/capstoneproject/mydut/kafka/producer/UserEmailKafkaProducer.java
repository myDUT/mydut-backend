package com.capstoneproject.mydut.kafka.producer;

import com.capstoneproject.mydut.kafka.constant.BKafkaConstant;
import com.capstoneproject.mydut.kafka.payload.SendUserRegistrationNotificationCommand;
import com.capstoneproject.mydut.kafka.topic.EmailKafkaTopic;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * @author vndat00
 * @since 10/20/2024
 */

@Component
@Log4j2
public class UserEmailKafkaProducer extends BKafkaProducer<Object> {
    protected UserEmailKafkaProducer(@Qualifier(value = BKafkaConstant.JSON_KAFKA_TEMPLATE) KafkaTemplate<String, Object> kafkaTemplate) {
        super(kafkaTemplate);
    }

    public void sendEmailForUserRegistrationNotification(SendUserRegistrationNotificationCommand payload) {
        send(new ProducerRecord<>(EmailKafkaTopic.COMMAND_EMAIL_TOPIC, payload));
    }
}
