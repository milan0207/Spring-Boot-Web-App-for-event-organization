package edu.bbte.idde.kmim2248.controller.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

@Component
public class DeleteAuthorizationInterceptor implements HandlerInterceptor {
    Logger logger = LoggerFactory.getLogger(DeleteAuthorizationInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws IOException {
        if ("DELETE".equalsIgnoreCase(request.getMethod())) {
            String authorizationHeader = request.getHeader("Authorization");

            if (!"Bearer valid-token".equals(authorizationHeader)) {
                logger.warn("[DeleteAuthorizationInterceptor] Unauthorized DELETE request: " + request.getRequestURI());
                response.sendError(HttpServletResponse.SC_FORBIDDEN,
                        "You are not authorized to delete this resource.");
                return false;
            }

            logger.info("[DeleteAuthorizationInterceptor] Authorized DELETE request: " + request.getRequestURI());
        }
        return true;
    }
}
