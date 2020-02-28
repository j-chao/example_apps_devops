package com.example.demo.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderOptions;
import reactor.kafka.sender.SenderRecord;
import reactor.kafka.sender.SenderResult;

public class KafkaProducer {
  private static final Logger log = LoggerFactory.getLogger(KafkaProducer.class.getName());

  private final KafkaSender<Integer, String> sender;
  private SimpleDateFormat dateFormat;

  public KafkaProducer(String bootstrapServers) {

    Map<String, Object> props = new HashMap<>();
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    props.put(ProducerConfig.CLIENT_ID_CONFIG, "demo-kafka-producer");
    props.put(ProducerConfig.ACKS_CONFIG, "all");
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class);
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    SenderOptions<Integer, String> senderOptions = SenderOptions.create(props);

    sender = KafkaSender.create(senderOptions);
  }

  public Flux<SenderResult<Object>> sendToKafka(
      String topic, Object payload, Object correlationMetadata) {
    dateFormat = new SimpleDateFormat("HH:mm:ss:SSS z dd MMM yyyy");

    SenderRecord<Integer, String, Object> message =
        SenderRecord.create(
            new ProducerRecord<>("patient-topic", payload.toString()), correlationMetadata);
    return sender
        .send(Flux.just(message))
        .doOnError(e -> log.error("Failed to send to Kafka, terminating.", e))
        .doOnNext(
            r -> {
              RecordMetadata metadata = r.recordMetadata();
              System.out.printf(
                  "Message %s sent successfully, topic-partition=%s-%d offset=%d timestamp=%s\n",
                  r.correlationMetadata(),
                  metadata.topic(),
                  metadata.partition(),
                  metadata.offset(),
                  dateFormat.format(new Date(metadata.timestamp())));
            })
        .doOnCancel(this::close);
  }

  public void close() {
    sender.close();
  }
}
