package com.example.demo.service;

import com.example.demo.model.Patient;
import com.example.demo.model.dto.PatientDTO;
import com.example.demo.persistence.PatientRepository;
import com.example.demo.util.KafkaProducer;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
@Data
public class PatientService {

  @Autowired private PatientRepository patientRepository;
  private KafkaProducer kafkaProducer;

  private static final String BOOTSTRAP_SERVERS = "172.28.33.50:32654";
  private static final String TOPIC = "demo-topic";
  //
  //  public void addPatient(Patient patient) throws InterruptedException {
  //    int count = 20;
  //    CountDownLatch latch = new CountDownLatch(count);
  //    KafkaProducer producer = new KafkaProducer(BOOTSTRAP_SERVERS);
  ////    producer.sendMessages(TOPIC, count, latch);
  //    latch.await(10, TimeUnit.SECONDS);
  //    producer.close();
  //  }

  public Flux<Patient> getAllPatients() {
    return patientRepository.findAll();
  }

  public Flux<Patient> getPatient() throws InterruptedException {
    int count = 20;
    CountDownLatch latch = new CountDownLatch(count);
    KafkaProducer producer = new KafkaProducer(BOOTSTRAP_SERVERS);
    //    producer.sendMessages(TOPIC, count, latch);
    latch.await(10, TimeUnit.SECONDS);
    producer.close();
    return patientRepository.findAll();
  }

  public void sendToKafka(PatientDTO patientDTO) {
    kafkaProducer.sendIt(TOPIC, patientDTO);
  }

  public void addPatient(Patient patient) {
    patientRepository.save(patient).subscribe();
  }
}
