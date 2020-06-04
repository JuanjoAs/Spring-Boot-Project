package com.juanjo.proyecto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.juanjo.proyecto.model.Inquilino;
import com.juanjo.proyecto.model.Alquiler;
import com.juanjo.proyecto.model.Casa;
import com.juanjo.proyecto.model.Role;
import com.juanjo.proyecto.model.User;
import com.juanjo.proyecto.repository.InquilinoRepository;
import com.juanjo.proyecto.repository.RoleRepository;
import com.juanjo.proyecto.repository.UserRepository;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service("inquilinoService")
public class InquilinoServiceImpl implements InquilinoService {

	@Autowired
	private InquilinoRepository inquilinoRepository;
	@Autowired
	private UserService userService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	

	@Override
	public void saveInquilino(Inquilino inquilino) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		inquilino.setUser(userService.findUserByEmail(((org.springframework.security.core.userdetails.User) auth.getPrincipal()).getUsername()));
		inquilinoRepository.save(inquilino);
	}

	@Override
	public List<Inquilino> findInquilinoByAlquilers(Alquiler alquiler) {
		// TODO Auto-generated method stub
		return inquilinoRepository.findByAlquilers(alquiler);
	}

	


	@Override
	public List<Inquilino> findInquilinoByUser(User user) {
		return inquilinoRepository.findByUser(user);
	}

	@Override
	public Inquilino findById(int id) {
		return inquilinoRepository.findById(id);
	}

	


}