package com.example.demo.service;

import com.example.demo.model.Patient;
import com.example.demo.persistence.PatientRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class PatientService {

  @Autowired private PatientRepository patientRepository;

  public List<Patient> getAllPatients() {
    return patientRepository.findAll();
  }

  public void addPatient(Patient patient) {
    patientRepository.save(patient);
  }
}
