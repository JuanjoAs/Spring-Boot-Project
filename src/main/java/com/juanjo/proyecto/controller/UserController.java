package com.juanjo.proyecto.controller;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.juanjo.proyecto.model.Alquiler;
import com.juanjo.proyecto.model.Casa;
import com.juanjo.proyecto.model.User;
import com.juanjo.proyecto.service.AlquilerService;
import com.juanjo.proyecto.service.CanvasService;
import com.juanjo.proyecto.service.CasaService;
import com.juanjo.proyecto.service.UserService;

import net.bytebuddy.dynamic.loading.PackageDefinitionStrategy.Definition.Undefined;

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private CasaService casaService;
	@Autowired
	private AlquilerService alquilerService;
	@Autowired
	private CanvasService canvasjsChartService;
	
	
	
	@RequestMapping(value = { "/prueba" },method = RequestMethod.GET)
	public String springMVC(ModelMap modelMap) {
		List<List<Map<Object, Object>>> canvasjsDataList = canvasjsChartService.getCanvasjsChartData();
		modelMap.addAttribute("dataPointsList", canvasjsDataList);
		return "graphs/homePrice";
	}
	@RequestMapping(value = { "/","/home" }, method = RequestMethod.GET)
	public ModelAndView home(ModelMap modelMap) {
		ModelAndView model = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			User user = userService.findUserByEmail(auth.getName());
			
			System.out.println("Existe usuario"+ ((org.springframework.security.core.userdetails.User) auth.getPrincipal()).getUsername());
			model.addObject("userName", user.getFirstname() + " " + user.getLastname());
			model.addObject("header", "sidebarLog");
			
		}
		else {
			//model.addObject("header", "sidebarLogOut");
			model.setViewName("home/landing-page");//si no esta logueado, se ira directamente al alnding page 
			return model;
		}
		List<List<Map<Object, Object>>> canvasjsDataList = canvasjsChartService.getCanvasjsChartData();
		modelMap.addAttribute("dataPointsList", canvasjsDataList);
		model.setViewName("graphs/homePrice");
		return model;
	}

	@RequestMapping(value = {  "/login" }, method = RequestMethod.GET)
	public ModelAndView login() {
		ModelAndView model = new ModelAndView();

		model.setViewName("home/landing-page");
		return model;
	}
	
	
	@RequestMapping(value = {  "/calendario" }, method = RequestMethod.GET)
	public ModelAndView calendario() {
		ModelAndView model = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			User user = userService.findUserByEmail(auth.getName());
			
			System.out.println("Existe usuario"+ ((org.springframework.security.core.userdetails.User) auth.getPrincipal()).getUsername());
			model.addObject("user", user.getCasas());
			
			model.addObject("userName", user.getFirstname() + " " + user.getLastname());
			model.addObject("header", "sidebarLog");
			 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			//Conseguir las reservas
			List<HashMap<String, Object>> fechas=new ArrayList<HashMap<String,Object>>();
			HashMap<String, Object> o1=null;
			for (Casa c : user.getCasas()) {
				for (Alquiler a : alquilerService.findAlquilerByCasa(c)) {
					System.out.println(a.toString());
					o1=new HashMap<String, Object>();
					o1.put("id",a.getId());
					o1.put("name",c.getNombre());
					o1.put("location",c.getNombre());
					o1.put("startDate",new Date(a.getFechaEntrada().getTime()));
					o1.put("endDate",new Date(a.getFechaSalida().getTime()));
					o1.put("color","#2C8FC9");
					fechas.add(o1);
				}
			}
			model.addObject("fechas", fechas);
			
		}
		else {
			//model.addObject("header", "sidebarLogOut");
			model.setViewName("home/landing-page");//si no esta logueado, se ira directamente al alnding page 
			return model;
		}
		model.addObject("alquiler", new Alquiler());
		model.setViewName("home/calendario");
		return model;
	}
	@RequestMapping(value = {  "/calendario" }, method = RequestMethod.POST)
	public ModelAndView calendarioPOST(WebRequest request) {
		System.out.println(request.getParameter("nombre"));
		System.out.println(request.getParameter("codigo"));
			Casa c=casaService.findCasaByCodVivienda(request.getParameter("casa"));
			Alquiler a=new Alquiler();
		
		  a.setCasa(c); 
		  a.setFechaEntrada(fechaEntrada);
		  a.setFechaSalida(fechaSalida);
		  a.setPrecioBase(precio);
		
		  casaService.saveCasa(a);
		 
		
		return new ModelAndView("redirect:/calendario");
	}

	@RequestMapping(value = { "/signup" }, method = RequestMethod.GET)
	public ModelAndView signup() {
		ModelAndView model = new ModelAndView();
		User user = new User();
		model.addObject("user", user);
		model.setViewName("user/signup");
		return model;
	}
	/*
	 * @RequestMapping(value = { "/añadirVivienda" }, method = RequestMethod.GET)
	 * public ModelAndView añadirVivienda() { ModelAndView model = new
	 * ModelAndView(); Casa casa = new Casa(); model.addObject("casa", casa);
	 * model.setViewName("casa/añadir"); return model; }
	 */

	@RequestMapping(value = { "/añadirVivienda" }, method = RequestMethod.POST)
	public ModelAndView añadirVivienda(WebRequest request) {
		System.out.println(request.getParameter("nombre"));
		System.out.println(request.getParameter("codigo"));
		Casa x=casaService.findCasaByCodVivienda(request.getParameter("codigo"));
		if(x==null) {
			Casa c=new Casa();
			c.setCodVivienda(request.getParameter("codigo"));
			c.setNombre(request.getParameter("nombre"));
			casaService.saveCasa(c);
		}else {
			System.out.println(x.toString());
			for(Casa cc :x.getUser().getCasas()) {
				System.out.println(cc.toString());
			}
		}
		return new ModelAndView("redirect:/home");
	}

	@RequestMapping(value = { "/signup" }, method = RequestMethod.POST)
	public ModelAndView createUser(@Valid User user, BindingResult bindingResult) {
		ModelAndView model = new ModelAndView();
		User userExists = userService.findUserByEmail(user.getEmail());

		if (userExists != null) {
			bindingResult.rejectValue("email", "error.user", "This email already exists!");
		}
		if (bindingResult.hasErrors()) {
			model.setViewName("user/signup");
		} else {
			userService.saveUser(user);
			model.addObject("msg", "User has been registered successfully!");
			model.addObject("user", new User());
			model.setViewName("user/signup");
		}

		return model;
	}

	
	@RequestMapping(value = { "/access_denied" }, method = RequestMethod.GET)
	public ModelAndView accessDenied() {
		ModelAndView model = new ModelAndView();
		model.setViewName("errors/access_denied");
		return model;
	}
}