package com.example.demo.controller;

import com.example.demo.model.Patient;
import com.example.demo.model.dto.PatientDTO;
import com.example.demo.model.mapper.PatientMapper;
import com.example.demo.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
public class PatientController {

  @Autowired PatientService patientService;
  private final PatientMapper patientMapper;

  @GetMapping(
      path = "/patients",
      produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  private Flux<Patient> getAllPatients() {
    return patientService.getAllPatients();
  }

  @PostMapping(
      path = "/patient",
      produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public void addPatient(@RequestBody PatientDTO patientDTO) {
    Patient patient = patientMapper.toPatient(patientDTO);
    patientService.addPatient(patient);
  }
}
