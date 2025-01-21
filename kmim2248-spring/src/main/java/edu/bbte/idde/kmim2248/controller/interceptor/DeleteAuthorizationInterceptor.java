package edu.bbte.idde.kmim2248.controller.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

@Component
public class DeleteAuthorizationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws IOException {
        if ("DELETE".equalsIgnoreCase(request.getMethod())) {
            String authorizationHeader = request.getHeader("Authorization");

            if (authorizationHeader == null || !authorizationHeader.equals("Bearer valid-token")) {
                System.out.println("[DeleteAuthorizationInterceptor] Unauthorized DELETE request: "
                        + request.getRequestURI());
                response.sendError(HttpServletResponse.SC_FORBIDDEN,
                        "You are not authorized to delete this resource.");
                return false;
            }

            System.out.println("[DeleteAuthorizationInterceptor] Authorized DELETE request: "
                    + request.getRequestURI());
        }
        return true;
    }
}
