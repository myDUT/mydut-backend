package com.capstoneproject.mydut.kafka.common;

import com.capstoneproject.mydut.kafka.intercepter.LoggingConsumerInterceptor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.core.env.Environment;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author vndat00
 * @since 10/18/2024
 */

public class BKafkaConsumerConfig {
    protected Map<String, Object> getDefaultConsumerConfig(KafkaProperties kafkaProperties) {
        var configMap = new HashMap<String, Object>();

        configMap.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        configMap.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaProperties.getConsumer().getGroupId());
        configMap.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        configMap.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);

        configMap.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, StringDeserializer.class);

        return configMap;
    }

    protected Map<String, Object> getStringConsumerConfig(KafkaProperties kafkaProperties) {
        var configMap = this.getDefaultConsumerConfig(kafkaProperties);

        configMap.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, StringDeserializer.class);

        return configMap;
    }

    protected Map<String, Object> getJsonConsumerConfig(KafkaProperties kafkaProperties) {
        var configMap = this.getDefaultConsumerConfig(kafkaProperties);

        configMap.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class);
        configMap.put(JsonDeserializer.TRUSTED_PACKAGES, "*");

        return configMap;
    }

    protected ConsumerFactory<String, String> getStringConsumerFactory(KafkaProperties kafkaProperties) {
        var configMap = getStringConsumerConfig(kafkaProperties);

        configMap.put(ConsumerConfig.INTERCEPTOR_CLASSES_CONFIG, LoggingConsumerInterceptor.class.getName());

        return new DefaultKafkaConsumerFactory<>(configMap);
    }

    protected ConsumerFactory<String, Object> getJsonConsumerFactory(KafkaProperties kafkaProperties) {
        var configMap = getJsonConsumerConfig(kafkaProperties);

        configMap.put(ConsumerConfig.INTERCEPTOR_CLASSES_CONFIG, LoggingConsumerInterceptor.class.getName());

        return new DefaultKafkaConsumerFactory<>(configMap);
    }

    protected KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> getStringKafkaListenerContainerFactory(
            KafkaProperties kafkaProperties,
            Environment environment) {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, String>();

        factory.setConsumerFactory(getStringConsumerFactory(kafkaProperties));
        factory.setConcurrency(environment.getProperty("app.kafka.consumer.concurrency", Integer.class, 1));

        return factory;
    }

    protected KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Object>> getJsonKafkaListerContainerFactory(
            KafkaProperties kafkaProperties,
            Environment environment
    ) {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, Object>();

        factory.setConsumerFactory(getJsonConsumerFactory(kafkaProperties));
        factory.setConcurrency(environment.getProperty("app.kafka.consumer.concurrency", Integer.class, 1));

        return factory;
    }

}
