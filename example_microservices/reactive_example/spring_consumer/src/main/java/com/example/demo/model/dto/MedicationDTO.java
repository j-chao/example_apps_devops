package com.example.demo.model.dto;

import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MedicationDTO {
  @NotNull private String medicationName;
}
