package com.example.patientms.controller;

import com.example.patientms.model.Patient;
import com.example.patientms.service.PatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class PatientController {

  @Autowired PatientService patientService;
  private static final Logger LOGGER = LoggerFactory.getLogger(PatientController.class);

  @CrossOrigin
  @GetMapping("/allPatients")
  public Iterable<Patient> getAllPatients() {
    LOGGER.info("I HAVE A REQUEST!");
    return patientService.getAllPatients();
  }

  @CrossOrigin
  @PostMapping("/patient")
  Patient addPatient(@RequestBody Patient patient) {
    return patientService.addPatient(patient);
  }
}
