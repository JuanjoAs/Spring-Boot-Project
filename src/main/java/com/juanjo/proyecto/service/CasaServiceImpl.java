package com.juanjo.proyecto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.juanjo.proyecto.model.Casa;
import com.juanjo.proyecto.model.Role;
import com.juanjo.proyecto.model.User;
import com.juanjo.proyecto.repository.CasaRepository;
import com.juanjo.proyecto.repository.RoleRepository;
import com.juanjo.proyecto.repository.UserRepository;
import java.util.Arrays;
import java.util.HashSet;

@Service("casaService")
public class CasaServiceImpl implements CasaService {

	@Autowired
	private CasaRepository casaRepository;
	@Autowired
	private UserService userService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public Casa findCasaByCodVivienda(String codVivienda) {
		return casaRepository.findByCodVivienda(codVivienda);
	}

	@Override
	public void saveCasa(Casa casa) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		casa.setActive(true);
		casa.setUser(userService.findUserByEmail(
				((org.springframework.security.core.userdetails.User) auth.getPrincipal()).getUsername()));
		casaRepository.save(casa);
	}

}