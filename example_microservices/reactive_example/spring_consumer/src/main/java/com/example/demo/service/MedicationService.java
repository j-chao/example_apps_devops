package com.example.demo.service;

import com.example.demo.model.Medication;
import com.example.demo.persistence.MedicationRepository;
import com.example.demo.util.KafkaProducer;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.kafka.sender.SenderResult;

@Service
@RequiredArgsConstructor
@Data
public class MedicationService {
  private static final Logger log = LoggerFactory.getLogger(MedicationService.class.getName());
  private static final String MEDICATION_TOPIC = "medication-topic";
  private KafkaProducer kafkaProducer = new KafkaProducer("172.28.33.50:32110");

  @Autowired private MedicationRepository medicationRepository;

  public Flux<Medication> getAllMedications() {
    return medicationRepository.findAll();
  }

  public Flux<SenderResult<Object>> addMedication(Medication medication) {
    return medicationRepository
        .save(medication)
        .thenMany(kafkaProducer.sendToKafka(MEDICATION_TOPIC, medication, medication))
        .doOnError(e -> log.error("Failed to save medication.", e));
  }
}
