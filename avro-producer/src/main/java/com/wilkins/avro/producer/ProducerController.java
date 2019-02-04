package com.wilkins.avro.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@EnableBinding(MessageChannels.class)
@Slf4j
@RequiredArgsConstructor
@RestController
public class ProducerController {

  private final MessageChannels messageChannels;

  @GetMapping("/produce")
  public void produceMessage(@RequestParam String message) {
    Request request = new Request();
    request.setMessage(message);
    Message<Request> requestMessage = MessageBuilder.withPayload(request).build();
    log.debug("sending message: {}", message);
    messageChannels.testRequest().send(requestMessage);
  }
}
