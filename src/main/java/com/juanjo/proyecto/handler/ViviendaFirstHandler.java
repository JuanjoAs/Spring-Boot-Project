package com.juanjo.proyecto.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

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
		}
        if(user.getCasas()!=null||!user.getCasas().isEmpty()) {
        	return Flow.CONTINUE;
        }

        //or return Flow.HALT to halt this request and prevent execution of the controller
        //you may also wish to redirect to a login page here
        return Flow.HALT;
    }

	
} 