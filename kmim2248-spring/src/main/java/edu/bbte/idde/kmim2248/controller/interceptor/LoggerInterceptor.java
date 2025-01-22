package edu.bbte.idde.kmim2248.controller.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoggerInterceptor implements HandlerInterceptor {

    Logger logger = LoggerFactory.getLogger(LoggerInterceptor.class);

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex) {
        logger.info("[LoggerInterceptor] Completed: " + request.getMethod() + " " + request.getRequestURI()
                + " with status " + response.getStatus());
        if (ex != null) {
            logger.error("[LoggerInterceptor] Exception: " + ex.getMessage());
        }
    }
}

