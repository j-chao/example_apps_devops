package com.example.demo.router;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import com.example.demo.handler.MedicationHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class MedicationRouter {

  @Bean
  public RouterFunction<ServerResponse> getMedication(MedicationHandler medicationHandler) {
    return route(GET("/allMedications"), medicationHandler::findAll)
        .and(route(POST("/addMedication"), medicationHandler::addMedication));
  }
}
