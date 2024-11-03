package com.capstoneproject.mydut.kafka.config;

import com.capstoneproject.mydut.kafka.common.BKafkaConsumerConfig;
import com.capstoneproject.mydut.kafka.constant.BKafkaConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

/**
 * @author vndat00
 * @since 10/19/2024
 */

@EnableKafka
@Configuration
@RequiredArgsConstructor
public class KafkaConsumerConfig extends BKafkaConsumerConfig {
    private final Environment environment;

    @Bean(name = BKafkaConstant.STRING_CONSUMER_FACTORY)
    @Primary
    public ConsumerFactory<String, String> stringConsumerFactory(KafkaProperties kafkaProperties) {
        return this.getStringConsumerFactory(kafkaProperties);
    }

    @Bean(name = BKafkaConstant.STRING_LISTENER_CONTAINER_FACTORY)
    @Primary
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> stringListenerContainerFactory(
            KafkaProperties kafkaProperties
    ) {
        return this.getStringKafkaListenerContainerFactory(kafkaProperties, environment);
    }

    @Bean(name = BKafkaConstant.JSON_CONSUMER_FACTORY)
    public ConsumerFactory<String, Object> jsonConsumerFactory(KafkaProperties kafkaProperties) {
        return this.getJsonConsumerFactory(kafkaProperties);
    }

    @Bean(name = BKafkaConstant.JSON_LISTENER_CONTAINER_FACTORY)
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Object>> jsonListenerContainerFactory(
            KafkaProperties kafkaProperties
    ) {
        return this.getJsonKafkaListerContainerFactory(kafkaProperties, environment);
    }
}
