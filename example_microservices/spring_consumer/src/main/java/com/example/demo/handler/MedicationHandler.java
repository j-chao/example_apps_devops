package com.example.demo.handler;

import com.example.demo.model.Medication;
import com.example.demo.model.dto.MedicationDTO;
import com.example.demo.model.mapper.MedicationMapper;
import com.example.demo.service.MedicationService;
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
public class MedicationHandler {
  @Autowired MedicationService medicationService;
  private final MedicationMapper medicationMapper;
  private static final String MEDICATION_TOPIC = "medication-topic";
  private KafkaProducer kafkaProducer = new KafkaProducer("172.28.33.50:32110");

  public Mono<ServerResponse> findAll(ServerRequest request) {
    return ServerResponse.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(medicationService.getAllMedications(), MedicationDTO.class);
  }

  public Mono<ServerResponse> addMedication(ServerRequest request) {
    Mono<Medication> medicationMono =
        request.bodyToMono(MedicationDTO.class).map(medicationMapper::toMedication);
    return medicationMono
        .flatMap(
            medication ->
                ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(medicationService.addMedication(medication), Medication.class))
        .doOnRequest(
            medication1 -> kafkaProducer.sendToKafka(MEDICATION_TOPIC, medication1, medication1));
  }
}
