package com.example.demo.model.mapper;

import com.example.demo.model.Patient;
import com.example.demo.model.dto.PatientDTO;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper
public interface PatientMapper {
  PatientDTO toPatientdTO(Patient patient);

  List<PatientDTO> toPatientDTOs(List<Patient> patients);

  Patient toPatient(PatientDTO patientDTO);
}
