package com.juanjo.proyecto.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

import com.juanjo.proyecto.model.Casa;
import com.juanjo.proyecto.model.User;
import com.juanjo.proyecto.service.UserService;
import com.kastkode.springsandwich.filter.api.BeforeHandler;
import com.kastkode.springsandwich.filter.api.Flow;

@Component
public class ViviendaFirstHandler implements BeforeHandler {
	@Autowired
	private UserService userService;
    @Override
    public Flow handle(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler, String[] flags) throws Exception {
        System.out.println("RestrictByVivienda logic executed, checking for those houses bb");
        User user=null;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			user = userService.findUserByEmail(auth.getName());
			for(Casa c:user.getCasas()) {
				System.out.println(c.getId());
			}
			if(!user.getCasas().isEmpty()){
				System.out.println("Hay casa asi que palante");
	        	return Flow.CONTINUE;
	        }
			else {
				response.sendRedirect("/nuevaCasa");
				System.out.println("necistas una casa");
		        return Flow.CONTINUE;
			}
		}
        

        //or return Flow.HALT to halt this request and prevent execution of the controller
        return Flow.CONTINUE;
    }

	
} 