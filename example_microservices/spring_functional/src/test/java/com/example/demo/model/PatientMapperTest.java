package com.example.demo.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.demo.model.dto.PatientDTO;
import org.modelmapper.ModelMapper;

public class PatientMapperTest {
  private static final ModelMapper modelMapper = new ModelMapper();

  //  @Test
  public void checkPatientMapping() {
    PatientDTO patientDto = new PatientDTO();
    patientDto.setFirstName("TestingFirstName");
    patientDto.setLastName("TestingLastName");

    Patient patient = modelMapper.map(patientDto, Patient.class);
    assertEquals(patientDto.getFirstName(), patient.getFirstName());
    assertEquals(patientDto.getLastName(), patient.getLastName());
  }
}
