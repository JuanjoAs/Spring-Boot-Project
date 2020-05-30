package com.juanjo.proyecto.service;

import java.util.List;
import java.util.Optional;

import com.juanjo.proyecto.model.Alquiler;
import com.juanjo.proyecto.model.Casa;
import com.juanjo.proyecto.model.User;

public interface AlquilerService {
 public void saveAlquiler(Alquiler Alquiler);

List<Alquiler> findAlquilerByCasa(Casa casa);
Alquiler findById(int id);
}