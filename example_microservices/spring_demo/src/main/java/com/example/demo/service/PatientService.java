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
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Data
public class PatientService {
  private static final Logger log = LoggerFactory.getLogger(KafkaProducer.class.getName());

  @Autowired private PatientRepository patientRepository;
  private KafkaProducer kafkaProducer = new KafkaProducer("172.28.33.50:32654");

  private static final String BOOTSTRAP_SERVERS = "172.28.33.50:32654";
  private static final String PATIENT_TOPIC = "demo-topic";

  public Flux<Patient> getAllPatients() {
    return patientRepository.findAll();
  }

  public Mono<Patient> addPatient(Patient patient) {
    kafkaProducer.sendToKafka(PATIENT_TOPIC, patient, patient).subscribe();

    return patientRepository.save(patient).doOnError(e -> log.error("Failed to save patient.", e));
  }
}
