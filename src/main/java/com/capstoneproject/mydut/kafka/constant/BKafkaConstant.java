package com.capstoneproject.mydut.kafka.constant;

/**
 * @author vndat00
 * @since 10/19/2024
 */
public class BKafkaConstant {
    private BKafkaConstant() {
        throw new UnsupportedOperationException("BKafkaConstant class cannot be instantiated");
    }

    public static final String JSON_CONSUMER_FACTORY = "JsonKafkaConsumerFactory";
    public static final String STRING_CONSUMER_FACTORY = "StringKafkaConsumerFactory";

    public static final String JSON_LISTENER_CONTAINER_FACTORY = "JsonKafkaListenerContainerFactory";
    public static final String STRING_LISTENER_CONTAINER_FACTORY = "StringKafkaListenerContainerFactory";

    public static final String JSON_KAFKA_TEMPLATE = "JsonKafkaTemplate";
    public static final String STRING_KAFKA_TEMPLATE = "StringKafkaTemplate";

    public static final String JSON_KAFKA_PRODUCER_FACTORY = "JsonKafkaProducerFactory";
    public static final String STRING_KAFKA_PRODUCER_FACTORY = "StringKafkaProducerFactory";
}
