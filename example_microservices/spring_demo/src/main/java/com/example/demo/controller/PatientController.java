package com.example.demo.controller;

import com.example.demo.model.Patient;
import com.example.demo.model.dto.PatientDTO;
import com.example.demo.service.PatientService;
import com.example.demo.util.DTO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PatientController {

  @Autowired private PatientService patientService;

  @GetMapping("/patients")
  public List<Patient> retrieveAllPatients() {
    return patientService.getAllPatients();
  }

  @PostMapping(path = "/patient", consumes = "application/json", produces = "application/json")
  public void addPatient(@DTO(PatientDTO.class) Patient patient) {
    patientService.addPatient(patient);
  }
}
