package com.example.demo.dao.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "patients")
public class Patient {
  @Id
  @GeneratedValue(strategy= GenerationType.SEQUENCE)
  private Long id;

  private String firstName;

  private String lastName;

  public Patient(String firstName, String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
  }
}
