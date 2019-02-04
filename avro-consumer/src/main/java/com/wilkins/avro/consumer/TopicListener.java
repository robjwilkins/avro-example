package com.wilkins.avro.consumer;

import java.nio.charset.Charset;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeType;

@Slf4j
@Component
@RequiredArgsConstructor
public class TopicListener {

  private final AvroKafkaMessageConverter messageConverter;

  @KafkaListener(topics = {"test-request"})
  public void listenForMessage(ConsumerRecord<?, ?> consumerRecord) {
    log.info("listenForMessage. got a message: {}", consumerRecord);
    consumerRecord.headers().forEach(
        header -> log.info("header. key: {}, value: {}", header.key(), asString(header.value())));
    Request request = (Request) messageConverter.convertFromInternal(
        consumerRecord, Request.class, MimeType.valueOf("application/vnd.*+avr"));
    log.info("request message: {}", request.getMessage());
//        String value = consumerRecord.value();
//        log.info("value: {}", value);
  }

  private String asString(byte[] byteArray) {
    return new String(byteArray, Charset.defaultCharset());
  }
}
