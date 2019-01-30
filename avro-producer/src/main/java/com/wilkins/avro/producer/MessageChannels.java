package com.wilkins.avro.producer;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface MessageChannels {

  @Output("test-request")
  MessageChannel testRequest();

}