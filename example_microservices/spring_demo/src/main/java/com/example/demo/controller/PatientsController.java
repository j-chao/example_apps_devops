package com.example.demo.controller;

import com.example.demo.dao.PatientRepository;
import com.example.demo.dao.entity.Patient;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PatientsController {

  @Autowired
  private PatientRepository patientRepository;

  @GetMapping("/patients")
  public List<Patient> retrieveAllPatientss() {
    return (List<Patient>) patientRepository.findAll();
  }

}
