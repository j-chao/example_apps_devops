package com.example.demo.persistence;

import com.example.demo.model.Patient;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
  List<Patient> findByLastName(String lastName);
  Patient findById(long id);
}
