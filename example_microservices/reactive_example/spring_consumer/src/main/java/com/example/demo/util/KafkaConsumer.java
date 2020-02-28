package com.example.demo.util;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOffset;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.receiver.ReceiverRecord;

public class KafkaConsumer {

  private static final Logger log = LoggerFactory.getLogger(KafkaConsumer.class.getName());

  private static final String BOOTSTRAP_SERVERS = "172.28.33.50:32110";
  private static final String TOPIC = "demo-topic";

  private final ReceiverOptions<Integer, String> receiverOptions;
  private final SimpleDateFormat dateFormat;

  public KafkaConsumer(String bootstrapServers) {

    Map<String, Object> props = new HashMap<>();
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    props.put(ConsumerConfig.CLIENT_ID_CONFIG, "sample-consumer");
    props.put(ConsumerConfig.GROUP_ID_CONFIG, "sample-group");
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class);
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
    receiverOptions = ReceiverOptions.create(props);
    dateFormat = new SimpleDateFormat("HH:mm:ss:SSS z dd MMM yyyy");
  }

  public Flux<ReceiverRecord<Integer, String>> consumeMessages(String topic) {

    ReceiverOptions<Integer, String> options =
        receiverOptions
            .subscription(Collections.singleton(topic))
            .addAssignListener(partitions -> log.debug("onPartitionsAssigned {}", partitions))
            .addRevokeListener(partitions -> log.debug("onPartitionsRevoked {}", partitions));
    Flux<ReceiverRecord<Integer, String>> kafkaFlux = KafkaReceiver.create(options).receive();
    return kafkaFlux.doOnNext(
        record -> {
          ReceiverOffset offset = record.receiverOffset();
          System.out.printf(
              "Received message: topic-partition=%s offset=%d timestamp=%s key=%d value=%s\n",
              offset.topicPartition(),
              offset.offset(),
              dateFormat.format(new Date(record.timestamp())),
              record.key(),
              record.value());
        });
  }
}
