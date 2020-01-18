package com.example.demo;

import com.example.demo.dao.PatientRepository;
import com.example.demo.dao.entity.Patient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

  public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
  }

  @Bean
  public CommandLineRunner demoPatients(PatientRepository repository) {
    return (args) -> {
      // save a few patients to the database
      repository.save(new Patient("Justin", "Chao"));
      repository.save(new Patient("Kim", "Madison"));
    };
  }
}
