package edu.bbte.idde.kmim2248.controller.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoggerInterceptor implements HandlerInterceptor {

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex) {
        System.out.println("[LoggerInterceptor] Completed: " + request.getMethod() + " " + request.getRequestURI()
                + " with status " + response.getStatus());
        if (ex != null) {
            System.err.println("[LoggerInterceptor] Exception: " + ex.getMessage());
        }
    }
}

