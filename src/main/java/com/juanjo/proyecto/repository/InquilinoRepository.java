package com.juanjo.proyecto.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.juanjo.proyecto.model.Alquiler;
import com.juanjo.proyecto.model.Casa;
import com.juanjo.proyecto.model.Inquilino;
import com.juanjo.proyecto.model.User;
@Repository("inquilinoRepository")
public interface InquilinoRepository extends JpaRepository<Inquilino, Long> {
 List<Inquilino> findByAlquilers(Alquiler alquiler);
 List<Inquilino> findByUser(User user);
 Inquilino findById(int id);
}