package com.example.demo;

import com.example.demo.util.DTOModelMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
  private final ApplicationContext applicationContext;
  private final EntityManager entityManager;

  @Autowired
  public WebMvcConfig(ApplicationContext applicationContext, EntityManager entityManager) {
    this.applicationContext = applicationContext;
    this.entityManager = entityManager;
  }

  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
    ObjectMapper objectMapper =
        Jackson2ObjectMapperBuilder.json().applicationContext(this.applicationContext).build();
    argumentResolvers.add(new DTOModelMapper(objectMapper, entityManager));
  }
}
