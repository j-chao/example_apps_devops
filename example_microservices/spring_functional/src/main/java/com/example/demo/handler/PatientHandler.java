package com.example.demo.handler;

import com.example.demo.model.Patient;
import com.example.demo.model.dto.PatientDTO;
import com.example.demo.model.mapper.PatientMapper;
import com.example.demo.service.PatientService;
import com.example.demo.util.KafkaProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class PatientHandler {
  @Autowired PatientService patientService;
  private final PatientMapper patientMapper;
  private static final String PATIENT_TOPIC = "demo-topic";
  private KafkaProducer kafkaProducer = new KafkaProducer("172.28.33.50:32110");

  public Mono<ServerResponse> findAll(ServerRequest request) {
    return ServerResponse.ok()
        .contentType(MediaType.TEXT_EVENT_STREAM)
        .body(patientService.getAllPatients(), PatientDTO.class);
  }

  public Mono<ServerResponse> createPatient(ServerRequest request) {
    Mono<Patient> patientMono = request.bodyToMono(PatientDTO.class).map(patientMapper::toPatient);
    return patientMono
        .flatMap(
            patient ->
                ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(patientService.addPatient(patient), Patient.class))
        .doOnRequest(patient1 -> kafkaProducer.sendToKafka(PATIENT_TOPIC, patient1, patient1));
  }
}
