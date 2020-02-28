package com.example.patientms.model.mapper;

import com.example.patientms.model.Patient;
import com.example.patientms.model.dto.PatientDTO;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper
public interface PatientMapper {
  List<PatientDTO> toPatientDTOs(List<Patient> patients);

  Patient toPatient(PatientDTO patientDTO);
}
