package com.wilkins.avro.consumer;

import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.schema.client.ConfluentSchemaRegistryClient;
import org.springframework.cloud.stream.schema.client.SchemaRegistryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SchemaRegistryConfig {

  @Bean
  public SchemaRegistryClient schemaRegistryClient(
      @Value("${spring.cloud.stream.schema-registry-client.endpoint}") @NotNull final String endpoint) {
    ConfluentSchemaRegistryClient client = new ConfluentSchemaRegistryClient();
//    DefaultSchemaRegistryClient client = new DefaultSchemaRegistryClient();
    client.setEndpoint(endpoint);
    return client;
  }
}

