package com.juanjo.proyecto.service;

import com.juanjo.proyecto.model.Casa;
import com.juanjo.proyecto.model.User;

public interface CasaService {
	public void saveCasa(Casa casa);

	Casa findCasaByCodVivienda(String codVivienda);
}