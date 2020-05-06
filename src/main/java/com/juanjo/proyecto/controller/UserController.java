package com.juanjo.proyecto.controller;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
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
import com.juanjo.proyecto.model.Notificacion;
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

	@RequestMapping(value = { "/prueba" }, method = RequestMethod.GET)
	public String springMVC(ModelMap modelMap) {
		List<List<Map<Object, Object>>> canvasjsDataList = canvasjsChartService.getCanvasjsChartData();
		modelMap.addAttribute("dataPointsList", canvasjsDataList);

		return "graphs/homePrice";
	}

	@RequestMapping(value = { "/", "/home" }, method = RequestMethod.GET)
	public ModelAndView home(ModelMap modelMap) {
		ModelAndView model = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			User user = userService.findUserByEmail(auth.getName());

			System.out.println("Existe usuario"
					+ ((org.springframework.security.core.userdetails.User) auth.getPrincipal()).getUsername());
			model.addObject("userName", user.getFirstname() + " " + user.getLastname());
			model.addObject("header", "sidebarLog");
			model.addObject("notificaciones", getNumNotificaciones());

		} else {
			// model.addObject("header", "sidebarLogOut");
			model.setViewName("home/landing-page");// si no esta logueado, se ira directamente al alnding page
			return model;
		}

		List<List<Map<Object, Object>>> canvasjsDataList = canvasjsChartService.getCanvasjsChartData();
		modelMap.addAttribute("dataPointsList", canvasjsDataList);
		User user = userService.findUserByEmail(auth.getName());

		int[] invierno = { 19, 2 };
		int[] primavera = { 20, 5 };
		int[] verano = { 21, 8 };
		int[] otoño = { 21, 11 };
		
		List<HashMap<String, Object>> pri = new ArrayList<HashMap<String, Object>>();
		List<HashMap<String, Object>> ver = new ArrayList<HashMap<String, Object>>();
		List<HashMap<String, Object>> oto = new ArrayList<HashMap<String, Object>>();
		List<HashMap<String, Object>> inv = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> obj = null;
		
		SimpleDateFormat curFormater = new SimpleDateFormat("MM/dd/yyyy"); 
		Date dateObj = new Date(); 
		for (Casa c : user.getCasas()) {

			for (Alquiler a : alquilerService.findAlquilerByCasa(c)) {
				
				GregorianCalendar calendar =  new GregorianCalendar();
				try {
					dateObj = curFormater.parse(a.getFechaEntrada()); 
					
					calendar.setTimeInMillis(dateObj.getTime());
//					System.out.println(a.getFechaEntrada()+"----"+invierno[1]+primavera[1]);
//					System.out.println("Mes:"+calendar.get(Calendar.MONTH));
//					System.out.println("Año:"+calendar.get(Calendar.YEAR));
//					System.out.println("Dia:"+calendar.get(Calendar.DAY_OF_MONTH));
					obj = new HashMap<String, Object>();
					obj.put("x", a.getFechaEntrada());
					obj.put("y", a.getPrecio());
					if (calendar.get(Calendar.MONTH) < invierno[1]) {
						if(calendar.get(Calendar.DAY_OF_MONTH) > invierno[0]&&calendar.get(Calendar.MONTH) == invierno[1]) {
							System.out.println("primavera");
							
							pri.add(obj);
						}else {
							System.out.println("invierno");
							inv.add(obj);
						}
						
					}  else if (calendar.get(Calendar.MONTH) < primavera[1]) {
						if(calendar.get(Calendar.DAY_OF_MONTH) > primavera[0]&&calendar.get(Calendar.MONTH) == primavera[1]) {
							System.out.println("verano");
							ver.add(obj);
						}else {
							System.out.println("primavera");
							pri.add(obj);
						}
					}else if (calendar.get(Calendar.MONTH) < verano[1]) {
						if(calendar.get(Calendar.DAY_OF_MONTH) > verano[0]&&calendar.get(Calendar.MONTH) == verano[1]) {
							System.out.println("otoño");
							oto.add(obj);
						}else {
							System.out.println("verano");
							ver.add(obj);
						}
					} else if (calendar.get(Calendar.MONTH) < otoño[1]) {
						if(calendar.get(Calendar.DAY_OF_MONTH) > otoño[0]&&calendar.get(Calendar.MONTH) == otoño[1]) {
							System.out.println("invierno");
							inv.add(obj);
						}else {
							System.out.println("otoño");
							oto.add(obj);
						}
					}	
					
					
					
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
		model.addObject("pri",pri);
		model.addObject("ver",ver);
		model.addObject("oto",oto);
		model.addObject("inv",inv);
		model.setViewName("graphs/homePrice");
		return model;
	}

	@RequestMapping(value = { "/notificaciones" }, method = RequestMethod.GET)
	public ModelAndView notificaciones(ModelMap modelMap) {
		ModelAndView model = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			User user = userService.findUserByEmail(auth.getName());

			System.out.println("Existe usuario"
					+ ((org.springframework.security.core.userdetails.User) auth.getPrincipal()).getUsername());
			model.addObject("userName", user.getFirstname() + " " + user.getLastname());
			model.addObject("header", "sidebarLog");
			model.addObject("notificacionesList", getNotificaciones());
			model.addObject("notificaciones", "");
		} else {
			model.setViewName("home/landing-page");// si no esta logueado, se ira directamente al alnding page
			return model;
		}

		model.setViewName("home/notificaciones");
		return model;
	}

	private String getNumNotificaciones() {
		String notificaciones = "";
		int x = 0;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			User user = userService.findUserByEmail(auth.getName());
			for (Casa c : user.getCasas()) {
				System.out.println("Casa:" + c);
				for (Alquiler a : alquilerService.findAlquilerByCasa(c)) {
					System.out.println("Alquiler:" + a);
					if (a.getInquilinos().isEmpty()) {
						x++;
					}
				}
			}
		}
		notificaciones += x;
		return notificaciones;
	}

	private List<Notificacion> getNotificaciones() {
		List<Notificacion> notificaciones = new ArrayList<Notificacion>();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			User user = userService.findUserByEmail(auth.getName());
			int x = 0;
			for (Casa c : user.getCasas()) {

				for (Alquiler a : alquilerService.findAlquilerByCasa(c)) {

					if (a.getInquilinos().isEmpty()) {
						notificaciones.add(new Notificacion("fas fa-user-cog fa-3x",
								"No has registrado ningún inquilino para esta reserva", "Gestionar ahora"));
					}
					x++;
				}
			}
		}
		for (Notificacion notificacion : notificaciones) {
			System.out.println(notificacion.getMensaje());
		}
		return notificaciones;
	}

	@RequestMapping(value = { "/login" }, method = RequestMethod.GET)
	public ModelAndView login() {
		ModelAndView model = new ModelAndView();

		model.setViewName("home/landing-page");
		return model;
	}

	@RequestMapping(value = { "/calendario" }, method = RequestMethod.GET)
	public ModelAndView calendario() {
		ModelAndView model = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			User user = userService.findUserByEmail(auth.getName());

			System.out.println("Existe usuario"
					+ ((org.springframework.security.core.userdetails.User) auth.getPrincipal()).getUsername());
			model.addObject("user", user.getCasas());
			model.addObject("notificaciones", getNumNotificaciones());
			model.addObject("userName", user.getFirstname() + " " + user.getLastname());
			model.addObject("header", "sidebarLog");
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			// Conseguir las reservas
			List<HashMap<String, Object>> fechas = new ArrayList<HashMap<String, Object>>();
			HashMap<String, Object> o1 = null;
			for (Casa c : user.getCasas()) {
				for (Alquiler a : alquilerService.findAlquilerByCasa(c)) {
					System.out.println(a.toString());
					o1 = new HashMap<String, Object>();
					o1.put("id", a.getId());
					o1.put("name", c.getNombre());
					o1.put("location", c.getNombre());
					o1.put("startDate", a.getFechaEntrada());
					o1.put("endDate", a.getFechaSalida());
					o1.put("color", "#2C8FC9");
					fechas.add(o1);
				}
			}
			model.addObject("fechas", fechas);

		} else {
			// model.addObject("header", "sidebarLogOut");
			model.setViewName("home/landing-page");// si no esta logueado, se ira directamente al alnding page
			return model;
		}
		model.addObject("alquiler", new Alquiler());
		model.setViewName("home/calendario");
		return model;
	}

	@RequestMapping(value = { "/calendarioActualizar" }, method = RequestMethod.POST)
	public ModelAndView calendarioPOST(WebRequest request) {
		Casa c = casaService.findCasaByCodVivienda(request.getParameter("casa"));
		System.out.println(c.toString());
		Alquiler a = new Alquiler();

		a.setCasa(c);
		a.setFechaEntrada(request.getParameter("event-start-date"));
		a.setFechaSalida(request.getParameter("event-end-date"));
		a.setPrecio(Float.parseFloat(request.getParameter("event-location")));

		alquilerService.saveAlquiler(a);
		System.out.println(a.toString());

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
		Casa x = casaService.findCasaByCodVivienda(request.getParameter("codigo"));
		if (x == null) {
			Casa c = new Casa();
			c.setCodVivienda(request.getParameter("codigo"));
			c.setNombre(request.getParameter("nombre"));
			casaService.saveCasa(c);
		} else {
			System.out.println(x.toString());
			for (Casa cc : x.getUser().getCasas()) {
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