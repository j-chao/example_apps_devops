package com.example.demo.model.dto;

import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PatientDTO {
  @NotNull private String firstName;

  @NotNull private String lastName;
}
