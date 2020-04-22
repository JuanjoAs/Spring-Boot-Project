package com.juanjo.proyecto.service;

import java.util.List;

import com.juanjo.proyecto.model.Alquiler;
import com.juanjo.proyecto.model.Casa;
import com.juanjo.proyecto.model.User;

public interface AlquilerService {
 public void saveAlquiler(Alquiler Alquiler);

List<Alquiler> findAlquilerByCasa(Casa casa);
}