package com.wilkins.avro.consumer;

import java.nio.charset.Charset;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TopicListener {

    @KafkaListener(topics = {"test-request"})
    public void listenForMessage(ConsumerRecord<String, Request> consumerRecord) {
        log.info("listenForMessage. got a message: {}", consumerRecord);
        consumerRecord.headers().forEach(header -> log.info("header. key: {}, value: {}", header.key(), asString(header.value())));
    }

//    @KafkaListener(topics = {"test-request"})
//    public void listenForMessage(ConsumerRecord<?, ?> consumerRecord) {
//        log.info("listenForMessage. got a message: {}", consumerRecord);
//        consumerRecord.headers().forEach(header -> log.info("header. key: {}, value: {}", header.key(), asString(header.value())));
//    }

    private String asString(byte[] byteArray) {
        return new String(byteArray, Charset.defaultCharset());
    }

}
