package com.juanjo.proyecto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.juanjo.proyecto.model.Alquiler;
import com.juanjo.proyecto.model.Casa;
import com.juanjo.proyecto.model.Role;
import com.juanjo.proyecto.model.User;
import com.juanjo.proyecto.repository.AlquilerRepository;
import com.juanjo.proyecto.repository.RoleRepository;
import com.juanjo.proyecto.repository.UserRepository;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service("alquilerService")
public class AlquilerServiceImpl implements AlquilerService {

	@Autowired
	private AlquilerRepository alquilerRepository;
	@Autowired
	private UserService userService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	

	@Override
	public void saveAlquiler(Alquiler alquiler) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		alquilerRepository.save(alquiler);
	}

	@Override
	public List<Alquiler> findAlquilerByCasa(Casa casa) {
		// TODO Auto-generated method stub
		return alquilerRepository.findByCasa(casa);
	}
	@Override
	public List<Alquiler> findAlquilerByUser(User user) {
		// TODO Auto-generated method stub
		return alquilerRepository.findByUser(user);
	}



	@Override
	public Alquiler findById(int id) {
		return alquilerRepository.findById(id);
	}


}