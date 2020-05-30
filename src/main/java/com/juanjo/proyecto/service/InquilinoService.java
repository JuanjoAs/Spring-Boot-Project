package com.juanjo.proyecto.service;

import java.util.List;
import java.util.Optional;

import com.juanjo.proyecto.model.Inquilino;
import com.juanjo.proyecto.model.Alquiler;
import com.juanjo.proyecto.model.User;

public interface InquilinoService {
 public void saveInquilino(Inquilino Inquilino);

List<Inquilino> findInquilinoByAlquilers(Alquiler alquiler);
Optional<Inquilino> findById(long id);
}