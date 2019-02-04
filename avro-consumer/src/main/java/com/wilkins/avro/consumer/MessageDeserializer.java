package com.wilkins.avro.consumer;

import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Deserializer;
import org.springframework.cache.support.NoOpCacheManager;
import org.springframework.cloud.stream.schema.client.ConfluentSchemaRegistryClient;
import org.springframework.cloud.stream.schema.client.SchemaRegistryClient;

@Slf4j
public class MessageDeserializer implements Deserializer<Request> {

  private AvroKafkaMessageConverter converter;

  @Override
  public void configure(Map<String, ?> configs, boolean isKey) {
    String schemaRegistryEndpoint = (String) configs.get("schema.registry.url");
    log.debug("schemaRegistryEndpoint: {}", schemaRegistryEndpoint);
    SchemaRegistryClient client = schemaRegistryClient(schemaRegistryEndpoint);
    converter = new AvroKafkaMessageConverter(client, new NoOpCacheManager());

  }

  @Override
  public Request deserialize(String topic, byte[] data) {
    log.debug("deserialize. topic: ()", topic);
    return converter.convert(topic, data);
  }

  @Override
  public void close() {

  }

  private SchemaRegistryClient schemaRegistryClient(String endpoint) {
    ConfluentSchemaRegistryClient client = new ConfluentSchemaRegistryClient();
//    DefaultSchemaRegistryClient client = new DefaultSchemaRegistryClient();
    client.setEndpoint(endpoint);
    return client;
  }
}
