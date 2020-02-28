package com.example.demo;

import com.example.demo.util.KafkaConsumer;
import com.example.demo.util.PostgresConfig;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@SpringBootApplication
@EnableR2dbcRepositories
@Import(PostgresConfig.class)
public class DemoApplication {

  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }

  public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
    KafkaConsumer consumer = new KafkaConsumer("172.28.33.50:32100");
    consumer.consumeMessages("demo-topic").subscribe();
  }
}
