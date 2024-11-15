package edu.bbte.idde.kmim2248;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter("/events-html")
public class AuthFilter implements Filter {
    @Override
    public void doFilter(jakarta.servlet.ServletRequest req, jakarta.servlet.ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request;
        HttpServletResponse response;
        if (req instanceof HttpServletRequest && res instanceof HttpServletResponse) {
            request = (HttpServletRequest) req;
            response = (HttpServletResponse) res;
        } else {
            throw new ServletException("Non-HTTP request or response");
        }
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("loggedIn") == null) {
            response.sendRedirect("login");
        } else {
            chain.doFilter(req, res);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void destroy() {
    }
}
