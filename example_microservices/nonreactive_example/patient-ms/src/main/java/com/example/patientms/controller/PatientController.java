package com.example.patientms.controller;

import com.example.patientms.model.Patient;
import com.example.patientms.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class PatientController {

  @Autowired PatientService patientService;

  @GetMapping(name = "/allPatients", produces = "application/json")
  public Iterable<Patient> getAllPatients() {
    return patientService.getAllPatients();
  }
}
