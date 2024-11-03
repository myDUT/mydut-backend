package com.capstoneproject.mydut.kafka.config;

import com.capstoneproject.mydut.kafka.common.BKafkaProducerConfig;
import com.capstoneproject.mydut.kafka.constant.BKafkaConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

/**
 * @author vndat00
 * @since 10/19/2024
 */

@Configuration
@RequiredArgsConstructor
public class KafkaProducerConfig extends BKafkaProducerConfig {
    @Bean(name = BKafkaConstant.STRING_KAFKA_PRODUCER_FACTORY)
    @Primary
    public ProducerFactory<String, String> stringProducerFactory(KafkaProperties kafkaProperties) {
        return this.getStringProducerFactory(kafkaProperties);
    }

    @Bean(name = BKafkaConstant.STRING_KAFKA_TEMPLATE)
    @Primary
    public KafkaTemplate<String, String> stringKafkaTemplate(KafkaProperties kafkaProperties) {
        return this.getStringKafkaTemplate(kafkaProperties);
    }

    @Bean(name = BKafkaConstant.JSON_KAFKA_PRODUCER_FACTORY)
    public ProducerFactory<String, Object> jsonProducerFactory(KafkaProperties kafkaProperties) {
        return this.getJsonProducerFactory(kafkaProperties);
    }

    @Bean(name = BKafkaConstant.JSON_KAFKA_TEMPLATE)
    public KafkaTemplate<String, Object> jsonKafkaTemplate(KafkaProperties kafkaProperties) {
        return this.getJsonKafkaTemplate(kafkaProperties);
    }

}
