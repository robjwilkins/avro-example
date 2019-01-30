package com.wilkins.avro.producer;

import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.schema.client.DefaultSchemaRegistryClient;
import org.springframework.cloud.stream.schema.client.SchemaRegistryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SchemaRegistryConfig {

  @Bean
  public SchemaRegistryClient defaultSchemaRegistryClient(
      @Value("${spring.cloud.stream.schema-registry-client.endpoint}") @NotNull final String endpoint) {
    DefaultSchemaRegistryClient client = new DefaultSchemaRegistryClient();
    client.setEndpoint(endpoint);
    return client;
  }
}

