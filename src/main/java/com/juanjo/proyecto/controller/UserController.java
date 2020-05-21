package com.juanjo.proyecto.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
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
import org.springframework.web.bind.annotation.ResponseBody;
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

			System.out.println("Existe usuario"+ ((org.springframework.security.core.userdetails.User) auth.getPrincipal()).getUsername());
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
		List<Alquiler> listaAlquiler = new ArrayList<Alquiler>();
		
		List<Map<String, Object>> pri = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> ver = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> oto = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> inv = new ArrayList<Map<String, Object>>();
		for (Casa c : user.getCasas()) {
			for (Alquiler a : alquilerService.findAlquilerByCasa(c)) {
				if(a.getPrecio()>0) {
					listaAlquiler.add(a);
				}
			}
		}
		SimpleDateFormat curFormater = new SimpleDateFormat("MM/dd/yyyy"); 
		Date dateObj = new Date(); 
		GregorianCalendar calendar =  new GregorianCalendar();
		
		for (Alquiler alquiler : listaAlquiler) {
			System.out.println("//////////////////////////////////////////////////");
			System.out.println(alquiler.getFechaEntrada());
			try {
				dateObj=curFormater.parse(alquiler.getFechaEntrada());
				calendar.setTimeInMillis(dateObj.getTime());
				Map xxx=new HashMap<String, Object>();
				xxx.put("x", calendar.get(Calendar.YEAR));
				xxx.put("y", alquiler.getPrecio());
				System.out.println("fecha: "+getSeason(dateObj));
				switch (getSeason(dateObj)) {
				
				
				case 0: 
					if (!pri.isEmpty()) {
						for (Map m : pri) {
							System.out.println("entra al if: " + m.get("x").equals(calendar.get(Calendar.YEAR)));
							if (m.get("x").equals(calendar.get(Calendar.YEAR))) {
								float precio = (float) m.get("y");
								m.put("y", (float) m.get("y") + (float) xxx.get("y"));
								xxx = null;
							}
						}
					}
					if(xxx!=null) {
						pri.add(xxx);
					}
					break;
				case 1:
					if (!ver.isEmpty()) {
						for (Map m : ver) {
							System.out.println("entra al if: " + m.get("x").equals(calendar.get(Calendar.YEAR)));
							if (m.get("x").equals(calendar.get(Calendar.YEAR))) {
								float precio = (float) m.get("y");
								m.put("y", (float) m.get("y") + (float) xxx.get("y"));
								xxx = null;
							}
						}
					}
					if(xxx!=null) {
						ver.add(xxx);
					}
					break;
				case 2:
					if (!oto.isEmpty()) {
						for (Map m : oto) {
							System.out.println("entra al if: " + m.get("x").equals(calendar.get(Calendar.YEAR)));
							if (m.get("x").equals(calendar.get(Calendar.YEAR))) {
								float precio = (float) m.get("y");
								m.put("y", (float) m.get("y") + (float) xxx.get("y"));
								xxx = null;
							}
						}
					}
					
					if(xxx!=null) {
						oto.add(xxx);
					}
					break;
				case 3:
					if (!inv.isEmpty()) {
						for (Map m : inv) {
							System.out.println(m);
							System.out.println("entra al if: " + m.get("x").equals(calendar.get(Calendar.YEAR)));
							if (m.get("x").equals(calendar.get(Calendar.YEAR))) {
								float precio = (float) m.get("y");
								m.put("y", (float) m.get("y") + (float) xxx.get("y"));
								xxx = null;
							}
						}
					}
					
					if(xxx!=null) {
						inv.add(xxx);
					}
					
					break;
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		model.addObject("pri",pri);
		model.addObject("ver",ver);
		model.addObject("oto",oto);
		model.addObject("inv",inv);
		model.setViewName("graphs/homePrice");
		return model;
	}
	
	
	public int getSeason(Date today){

	    // the months are one less because GC is 0-based for the months, but not days.
	    // i.e. 0 = January.
	   
	    GregorianCalendar dateToday = new GregorianCalendar();
	    dateToday.setTime(today);
	    int year=dateToday.get(Calendar.YEAR);
	    System.out.println("Año:"+ year+"--- fecha: "+dateToday.get(Calendar.DAY_OF_MONTH)+"/"+dateToday.get(Calendar.MONTH)+"/"+dateToday.get(Calendar.YEAR));
	    GregorianCalendar springstart = new GregorianCalendar(year, 2, 20);
	    GregorianCalendar summerstart = new GregorianCalendar(year, 4, 20);
	    GregorianCalendar fallstart = new GregorianCalendar(year, 8, 20);
	    GregorianCalendar winterstart = new GregorianCalendar(year, 10, 20);
	    System.out.println("Primavera: "+springstart.get(Calendar.DAY_OF_MONTH)+"/"+springstart.get(Calendar.MONTH)+"/"+springstart.get(Calendar.YEAR));
	    System.out.println("Verano: "+summerstart.get(Calendar.DAY_OF_MONTH)+"/"+summerstart.get(Calendar.MONTH)+"/"+summerstart.get(Calendar.YEAR));
	    System.out.println("Otoño: "+fallstart.get(Calendar.DAY_OF_MONTH)+"/"+fallstart.get(Calendar.MONTH)+"/"+fallstart.get(Calendar.YEAR));
	    System.out.println("Invierno: "+winterstart.get(Calendar.DAY_OF_MONTH)+"/"+winterstart.get(Calendar.MONTH)+"/"+winterstart.get(Calendar.YEAR));

	    if (dateToday.after(springstart)&&dateToday.before(summerstart))
	        return 0;//Primavera

	    else if (dateToday.after(summerstart)&&dateToday.before(fallstart))
	        return 1;//Verano

	    else if (dateToday.after(fallstart)&&dateToday.before(winterstart))
	        return 2;//Otoño

	    else if (dateToday.after(winterstart)||dateToday.before(springstart))
	        return 3;//Invierno

	    else 
	        return -1;//Error
	    
	   
	}
	@RequestMapping(value = { "/notificaciones" }, method = RequestMethod.GET)
	public ModelAndView notificaciones(ModelMap modelMap) {
		ModelAndView model = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			User user = userService.findUserByEmail(auth.getName());
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
								"No has registrado ningún inquilino para esta reserva. (COD:"+a.getId()+")", "/gestionar/"+a.getId()));
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
	
	@RequestMapping(value = { "/nuevoInquilino" }, method = RequestMethod.GET)
	public ModelAndView crearInquilino() {
		ModelAndView model = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			User user = userService.findUserByEmail(auth.getName());
			model.addObject("user", user.getCasas());
			model.addObject("notificaciones", getNumNotificaciones());
			model.addObject("userName", user.getFirstname() + " " + user.getLastname());
			model.addObject("header", "sidebarLog");
			
		} else {
			// model.addObject("header", "sidebarLogOut");
			model.setViewName("home/landing-page");// si no esta logueado, se ira directamente al alnding page
			return model;
		}
		
		model.setViewName("inquilino/nuevo");
		return model;
	}
	@RequestMapping(value = { "/noticias" }, method = RequestMethod.GET)
	public ModelAndView noticias() {
		ModelAndView model = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			User user = userService.findUserByEmail(auth.getName());
			model.addObject("user", user.getCasas());
			model.addObject("notificaciones", getNumNotificaciones());
			model.addObject("userName", user.getFirstname() + " " + user.getLastname());
			model.addObject("header", "sidebarLog");
			
		} else {
			// model.addObject("header", "sidebarLogOut");
			model.setViewName("home/landing-page");// si no esta logueado, se ira directamente al alnding page
			return model;
		}
		model.addObject("noticias",getNoticias());
		model.setViewName("home/noticias");
		return model;
	}
	@RequestMapping(value = { "/txt" }, method = RequestMethod.GET)
	public @ResponseBody String txtResponse(){
	    
	    
	    String data="";
	    try {
	        File myObj = new File("C:\\Users\\Juanjo\\Documents\\NetBeansProjects\\proyecto-final-grado-funcional\\src\\main\\resources\\templates\\noticias.txt");
	        Scanner myReader = new Scanner(myObj);
	        while (myReader.hasNextLine()) {
	           data+= myReader.nextLine();
	          
	        }
	        myReader.close();
	      } catch (FileNotFoundException e) {
	        System.out.println("An error occurred.");
	        e.printStackTrace();
	      }
	    
	    return data;
	}
	private List<String> getNoticias()  {
		List<String> list=new ArrayList<String>();
		String data[];
		
		URL url = null;
		try {
			url = new URL("http://localhost:8080/txt");
		
        BufferedReader read = new BufferedReader(new InputStreamReader(url.openStream()));
        String i;
        while ((i = read.readLine()) != null) {
		        
		       System.out.println(i);
		       list.add(i);
		      }
        read.close();    
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
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