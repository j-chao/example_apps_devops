package com.example.demo.controller;

import com.example.demo.model.Patient;
import com.example.demo.model.dto.PatientDTO;
import com.example.demo.model.mapper.PatientMapper;
import com.example.demo.service.PatientService;
import com.example.demo.util.KafkaProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.kafka.sender.SenderResult;

@RestController
@RequiredArgsConstructor
public class PatientController {

  @Autowired PatientService patientService;
  private KafkaProducer kafkaProducer;
  private final PatientMapper patientMapper;

  @GetMapping(path = "/patients", produces = MediaType.APPLICATION_JSON_VALUE)
  private Flux<Patient> getAllPatients() {
    return patientService.getAllPatients();
  }

  @GetMapping(path = "/kafka", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  private Flux<Patient> getPatient() throws InterruptedException {
    return patientService.getPatient();
  }

  @PostMapping(
      path = "/patient",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public Flux<SenderResult<Integer>> addPatient(@RequestBody PatientDTO patientDTO)
      throws InterruptedException {

    KafkaProducer producer = new KafkaProducer("172.28.33.50:32654");

    Patient patient = patientMapper.toPatient(patientDTO);
    patientService.addPatient(patient);

    return producer.sendIt("demo-topic", patientDTO);
  }
}
