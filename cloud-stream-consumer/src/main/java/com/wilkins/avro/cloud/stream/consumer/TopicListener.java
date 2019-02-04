package com.wilkins.avro.cloud.stream.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;

@Slf4j
@EnableBinding(MessageChannels.class)
public class TopicListener {

  @StreamListener("input-message")
  public void processMessage(@Payload Request request) {
    log.info("request message: {}", request.getMessage());
  }
}
