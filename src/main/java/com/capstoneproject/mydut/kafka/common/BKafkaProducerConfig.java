package com.capstoneproject.mydut.kafka.common;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author vndat00
 * @since 10/18/2024
 */

public class BKafkaProducerConfig {
    protected Map<String, Object> getStringProducerConfig(KafkaProperties kafkaProperties) {
        var configMap = getDefaultProducerConfig(kafkaProperties);

        configMap.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        return configMap;
    }

    protected Map<String, Object> getJsonProducerConfig(KafkaProperties kafkaProperties) {
        var configMap = getDefaultProducerConfig(kafkaProperties);

        configMap.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return configMap;
    }
    protected Map<String, Object> getDefaultProducerConfig(KafkaProperties kafkaProperties) {
        var configMap = new HashMap<String, Object>();

        configMap.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        configMap.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        return configMap;
    }

    protected ProducerFactory<String, String> getStringProducerFactory(KafkaProperties kafkaProperties) {
        return new DefaultKafkaProducerFactory<>(this.getStringProducerConfig(kafkaProperties));
    }

    protected ProducerFactory<String, Object> getJsonProducerFactory(KafkaProperties kafkaProperties) {
        return new DefaultKafkaProducerFactory<>(this.getJsonProducerConfig(kafkaProperties));
    }

    protected KafkaTemplate<String, String> getStringKafkaTemplate(KafkaProperties kafkaProperties) {
        return new KafkaTemplate<>(this.getStringProducerFactory(kafkaProperties));
    }

    protected KafkaTemplate<String, Object> getJsonKafkaTemplate(KafkaProperties kafkaProperties) {
        return new KafkaTemplate<>(this.getJsonProducerFactory(kafkaProperties));
    }
}
