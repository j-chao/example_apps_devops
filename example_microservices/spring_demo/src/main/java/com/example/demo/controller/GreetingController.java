package com.example.demo.controller;

import com.example.demo.service.PatientService;
import com.example.demo.util.KafkaProducer;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.kafka.sender.SenderResult;

@RestController
public class GreetingController {

  @Autowired PatientService patientService;

  private KafkaProducer kafkaProducer;
  private static final String template = "Hello, %s!";
  private final AtomicLong counter = new AtomicLong();

//  @GetMapping("/greeting")
//  public Flux<SenderResult<Integer>> greeting(@RequestParam(value = "name", defaultValue = "World") String name)
//      throws InterruptedException {
//
//    KafkaProducer producer = new KafkaProducer("172.28.33.50:32654");
//    return producer.sendIt("demo-topic", );
//    return new Greeting(counter.incrementAndGet(), String.format(template, name));
//  }
}
