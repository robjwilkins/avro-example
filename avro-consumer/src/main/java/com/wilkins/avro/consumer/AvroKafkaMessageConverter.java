package com.wilkins.avro.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cloud.stream.schema.avro.AvroSchemaRegistryClientMessageConverter;
import org.springframework.cloud.stream.schema.client.SchemaRegistryClient;

@Slf4j
public class AvroKafkaMessageConverter extends AvroSchemaRegistryClientMessageConverter {

  /**
   * Creates a new instance, configuring it with {@link SchemaRegistryClient} and {@link
   * CacheManager}.
   *
   * @param schemaRegistryClient the {@link SchemaRegistryClient} used to interact with the schema
   * registry server.
   * @param cacheManager instance of {@link CacheManager} to cache parsed schemas. If caching is not
   * required use {@link NoOpCacheManager}
   */
  public AvroKafkaMessageConverter(SchemaRegistryClient schemaRegistryClient, CacheManager cacheManager) {
    super(schemaRegistryClient, cacheManager);
  }

  public <T> T convert(String topic, byte[] data) {
    log.debug("deserialize. topic: {}", topic);

    Request request = new Request();
    request.setMessage("hello deserialized world");
    return (T)request;
  }
}
