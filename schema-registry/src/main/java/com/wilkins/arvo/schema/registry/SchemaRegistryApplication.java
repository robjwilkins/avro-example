package com.wilkins.arvo.schema.registry;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.schema.server.EnableSchemaRegistryServer;

@SpringBootApplication
@EnableSchemaRegistryServer
public class SchemaRegistryApplication {

  public static void main(String[] args) {
    SpringApplication.run(SchemaRegistryApplication.class, args);
  }
}
