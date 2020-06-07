package com.juanjo.proyecto.handler;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import com.kastkode.springsandwich.filter.api.BeforeHandler;
import com.kastkode.springsandwich.filter.api.Flow;

@Component
public class ConsoleLogger implements BeforeHandler {

    Logger logger = LoggerFactory.getLogger(ConsoleLogger.class);

    @Override
    public Flow handle(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler, String[] flags) throws Exception {
        logger.debug(request.getMethod() + "request is executing on");
        System.out.println("----------------");
        return Flow.CONTINUE;
    }
}