package com.wilkins.avro.cloud.stream.consumer;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface MessageChannels {

    @Input("input-message")
    SubscribableChannel inputMessage();
}
