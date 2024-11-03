package com.capstoneproject.mydut.kafka.producer;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;

/**
 * @author vndat00
 * @since 10/19/2024
 */

@Log4j2
public abstract class BKafkaProducer<T> {
    protected final KafkaTemplate<String, T> kafkaTemplate;

    protected BKafkaProducer(KafkaTemplate<String, T> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    protected void send(ProducerRecord<String, T> message) {
        kafkaTemplate.send(message).completable().handleAsync((resp, err) -> {
            if (err != null) {
                onSendFailed(err);
            }
            return resp;
        });
    }

    protected void onSendFailed(Throwable error) {
        log.error("Failed to send message. {}", ExceptionUtils.getMessage(error));
        //noinspection CallToPrintStackTrace
        error.printStackTrace();
    }


}
