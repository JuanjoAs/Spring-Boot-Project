package com.juanjo.proyecto.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.juanjo.proyecto.model.Casa;
import com.juanjo.proyecto.model.User;
@Repository("casaRepository")
public interface CasaRepository extends JpaRepository<Casa, Long> {
 Casa findByCodVivienda(String codVivienda);

}