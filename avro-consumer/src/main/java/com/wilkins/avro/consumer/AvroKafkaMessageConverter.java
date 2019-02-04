package com.wilkins.avro.consumer;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.Schema;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.cache.support.NoOpCacheManager;
import org.springframework.cloud.stream.schema.avro.AvroSchemaRegistryClientMessageConverter;
import org.springframework.cloud.stream.schema.client.SchemaRegistryClient;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.InvalidMimeTypeException;
import org.springframework.util.MimeType;

@Component
@Slf4j
public class AvroKafkaMessageConverter extends AvroSchemaRegistryClientMessageConverter {

  public AvroKafkaMessageConverter(SchemaRegistryClient schemaRegistryClient) {
    super(schemaRegistryClient, new NoOpCacheManager());
  }

  public Object convertFromInternal(ConsumerRecord<?, ?> consumerRecord, Class<?> targetClass,
      Object conversionHint) {
    Object result;
    try {
      byte[] payload = (byte[]) consumerRecord.value();

      Map<String, String> headers = new HashMap<>();
      consumerRecord.headers().forEach(header -> headers.put(header.key(), asString(header.value())));

      MimeType mimeType = messageMimeType(conversionHint, headers);
      if (mimeType == null) {
        return null;
      }

      Schema writerSchema = resolveWriterSchemaForDeserialization(mimeType);
      Schema readerSchema = resolveReaderSchemaForDeserialization(targetClass);

      @SuppressWarnings("unchecked")
      DatumReader<Object> reader = getDatumReader((Class<Object>) targetClass, readerSchema, writerSchema);
      Decoder decoder = DecoderFactory.get().binaryDecoder(payload, null);
      result = reader.read(null, decoder);
    }
    catch (IOException e) {
      throw new RuntimeException("Failed to read payload", e);
    }
    return result;
  }

  private MimeType messageMimeType(Object conversionHint, Map<String, String> headers) {
    MimeType mimeType;
    try {
      String contentType = headers.get(MessageHeaders.CONTENT_TYPE);
      log.debug("contentType: {}", contentType);
      mimeType = MimeType.valueOf(contentType);
    } catch (InvalidMimeTypeException e) {
      log.error("Exception getting object MimeType from contentType header", e);
      if (conversionHint instanceof MimeType) {
        mimeType = (MimeType) conversionHint;
      }
      else {
        return null;
      }
    }
    return mimeType;
  }

  private String asString(byte[] byteArray) {
    String theString = new String(byteArray, Charset.defaultCharset());
    return theString.replace("\"", "");
  }
}
