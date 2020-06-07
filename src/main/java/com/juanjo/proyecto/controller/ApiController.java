package com.juanjo.proyecto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.juanjo.proyecto.model.Alquiler;
import com.juanjo.proyecto.model.Casa;
import com.juanjo.proyecto.service.AlquilerService;
import com.juanjo.proyecto.service.CasaService;
import com.juanjo.proyecto.service.InquilinoService;
import com.juanjo.proyecto.service.UserService;

@RestController
public class ApiController {
	@Autowired
	private UserService userService;
	@Autowired
	private CasaService casaService;
	@Autowired
	private AlquilerService alquilerService;
	@Autowired
	private InquilinoService inquilinoService;



	

	@GetMapping("/greeting")
	public String greeting(
			@RequestParam(value = "casa") String casa,
			@RequestParam(value = "fechaEntrada") String fechaEntrada,
			@RequestParam(value = "fechaSalida") String fechaSalida,
			@RequestParam(value = "precio") String precio,
			@RequestParam(value = "inquilino") String inquilino
			) {
		if(casa!=null&&fechaEntrada!=null&&fechaSalida!=null&&precio!=null&&inquilino!=null) {
			Casa c = casaService.findCasaByCodVivienda(casa);
			Alquiler a = new Alquiler();
			a.setCasa(c);
			a.setFechaEntrada(fechaEntrada);
			a.setFechaSalida(fechaSalida);
			a.setPrecio(Float.parseFloat(precio));
			if(inquilino!=null) {
				a.setInquilino(inquilinoService.findById(Integer.parseInt(inquilino)));
			}
			alquilerService.saveAlquiler(a);
			return "succed";
		}else {
			return "error";
		}
		
		
	}
}