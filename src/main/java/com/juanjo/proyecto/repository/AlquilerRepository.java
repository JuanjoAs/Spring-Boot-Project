package com.juanjo.proyecto.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.juanjo.proyecto.model.Alquiler;
import com.juanjo.proyecto.model.Casa;
import com.juanjo.proyecto.model.User;
@Repository("alquilerRepository")
public interface AlquilerRepository extends JpaRepository<Alquiler, Long> {
 List<Alquiler> findByCasa(Casa casa);
}