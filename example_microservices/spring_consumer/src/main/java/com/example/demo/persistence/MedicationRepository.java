package com.example.demo.persistence;

import com.example.demo.model.Medication;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicationRepository extends ReactiveCrudRepository<Medication, Long> {}
