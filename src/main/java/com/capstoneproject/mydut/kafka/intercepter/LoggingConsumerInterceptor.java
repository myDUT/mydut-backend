package com.capstoneproject.mydut.kafka.intercepter;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.consumer.ConsumerInterceptor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.header.Header;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

/**
 * @author vndat00
 * @since 10/18/2024
 */

@Log4j2
public class LoggingConsumerInterceptor implements ConsumerInterceptor<String, Object> {
    @Override
    public ConsumerRecords<String, Object> onConsume(ConsumerRecords<String, Object> records) {
        for (var p : records.partitions()) {
            this.logCustomHeader(records.records(p));
        }
        return records;
    }

    private void logCustomHeader(List<ConsumerRecord<String, Object>> records) {
        for (var record : records) {
            var customHeader = new ArrayList<Header>();

            for (var header : record.headers()) {
                customHeader.add(header);
            }

            var joiner = new StringJoiner(", ");
            for (var header : customHeader) {
                var format = String.format("%s: %s", header.key(), new String(header.value()));
                joiner.add(format);
            }

            var headerString = joiner.toString();

            if (StringUtils.isNotBlank(headerString)) {
                log.info("=".repeat(10));
                log.info("Kafka custom headers: [{}]", headerString);
            } else {
                log.debug("No custom header was included for message from topic {} at offset {}", record.topic(), record.offset());
            }
        }
    }

    @Override
    public void onCommit(Map<TopicPartition, OffsetAndMetadata> map) {

    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
