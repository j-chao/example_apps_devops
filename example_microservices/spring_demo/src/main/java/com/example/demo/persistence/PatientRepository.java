package com.example.demo.persistence;

import com.example.demo.model.Patient;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

@Configuration
public interface PatientRepository extends ReactiveCrudRepository<Patient, Long> {

  //  Mono<Patient> findByLastName(String lastName);
}
