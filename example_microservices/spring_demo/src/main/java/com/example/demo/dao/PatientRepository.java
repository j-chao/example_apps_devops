package com.example.demo.dao;

import com.example.demo.dao.entity.Patient;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface PatientRepository extends CrudRepository<Patient, Long> {
  List<Patient> findByLastName(String lastName);

  Patient findById(long id);
}
