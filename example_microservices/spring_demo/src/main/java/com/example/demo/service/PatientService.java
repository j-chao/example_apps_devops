package com.example.demo.service;

import com.example.demo.model.Patient;
import com.example.demo.persistence.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class PatientService {

  @Autowired
  private PatientRepository patientRepository;

  public Flux<Patient> getAllPatients() {
    return patientRepository.findAll();
  }

  public void addPatient(Patient patient) {
    patientRepository.save(patient).subscribe();
  }
}
