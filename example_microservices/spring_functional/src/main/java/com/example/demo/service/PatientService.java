package com.example.demo.service;

import com.example.demo.model.Patient;
import com.example.demo.persistence.PatientRepository;
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
public class PatientService {
  private static final Logger log = LoggerFactory.getLogger(PatientService.class.getName());
  private static final String PATIENT_TOPIC = "demo-topic";
  private KafkaProducer kafkaProducer = new KafkaProducer("172.28.33.50:32110");

  @Autowired private PatientRepository patientRepository;

  public Flux<Patient> getAllPatients() {
    return patientRepository.findAll();
  }

  public Flux<SenderResult<Object>> addPatient(Patient patient) {
    return patientRepository
        .save(patient)
        .thenMany(kafkaProducer.sendToKafka(PATIENT_TOPIC, patient, patient))
        .doOnError(e -> log.error("Failed to save patient.", e));
  }
}
