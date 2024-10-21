package com.api.location.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    // Configure Spring pour servir les fichiers depuis le dossier 'static/uploads'
    registry.addResourceHandler("/uploads/**")
      .addResourceLocations("classpath:/uploads/");
  }
}
