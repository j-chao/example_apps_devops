package com.example.demo.persistence;

import com.example.demo.model.Patient;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends ReactiveCrudRepository<Patient, Long> {}
