package com.example.patientms.service;

import com.example.patientms.model.Patient;
import com.example.patientms.persistence.PatientRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Data
public class PatientService {
  private static final Logger log = LoggerFactory.getLogger(PatientService.class.getName());

  @Autowired private PatientRepository patientRepository;

  public Iterable<Patient> getAllPatients() {
    return patientRepository.findAll();
  }

  public Patient addPatient(Patient patient) {
    return patientRepository.save(patient);
  }
}
