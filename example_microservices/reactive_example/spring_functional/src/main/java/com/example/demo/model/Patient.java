package com.example.demo.model;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table("patients")
public class Patient {
  @Id private Long id;

  @NotNull
  @Column("first_name")
  private String firstName;

  @NotNull
  @Column("last_name")
  private String lastName;
}
