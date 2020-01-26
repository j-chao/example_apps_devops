package com.example.demo.model.mapper;

import com.example.demo.model.Medication;
import com.example.demo.model.dto.MedicationDTO;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper
public interface MedicationMapper {
  List<MedicationDTO> toMedicationDTO(List<Medication> medications);

  Medication toMedication(MedicationDTO medicationDTO);
}
