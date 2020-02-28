package com.example.demo.router;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import com.example.demo.handler.PatientHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class PatientRoute {

  @Bean
  public RouterFunction<ServerResponse> getAllPatientsRoute(PatientHandler patientHandler) {
    return route(GET("/allPatients"), patientHandler::findAll)
        .and(route(POST("/addPatient"), patientHandler::createPatient));
  }
}
