package com.juanjo.proyecto.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.juanjo.proyecto.repository.CanvasRepository;
import com.juanjo.proyecto.repository.CanvasRepositoryImpl;
import com.juanjo.proyecto.service.CanvasService;
import com.juanjo.proyecto.service.CanvasServiceImpl;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
 
 @Bean
 public BCryptPasswordEncoder passwordEncoder() {
  BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
  return bCryptPasswordEncoder;
 }
 @Bean
 public CanvasService canvasService() {
     return new CanvasServiceImpl();
 }
 @Bean
 public CanvasRepository canvasRepository() {
     return new CanvasRepositoryImpl();
 }
}