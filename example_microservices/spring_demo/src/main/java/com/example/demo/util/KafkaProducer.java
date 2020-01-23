package com.example.demo.util;

import com.example.demo.model.Patient;
import com.example.demo.model.dto.PatientDTO;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
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

  public KafkaProducer(String bootstrapServers) {

    Map<String, Object> props = new HashMap<>();
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    props.put(ProducerConfig.CLIENT_ID_CONFIG, "sample-producer");
    props.put(ProducerConfig.ACKS_CONFIG, "all");
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class);
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    SenderOptions<Integer, String> senderOptions = SenderOptions.create(props);

    sender = KafkaSender.create(senderOptions);
  }

  public Flux<SenderResult<Integer>> sendIt(String topic, Object payload) {
    SenderRecord<Integer, String, Integer> message =
        SenderRecord.create(new ProducerRecord<>("demo-topic", payload.toString()), 1);
    return sender
        .send(Flux.just(message))
        .doOnError(e -> log.error("Send failed, terminating.", e))
        .doOnNext(
            r -> {
              int id = r.correlationMetadata();
              log.trace("Successfully stored person with id {} in Kafka", id);
            })
        .doOnCancel(this::close);
  }

  public void close() {
    sender.close();
  }
}
