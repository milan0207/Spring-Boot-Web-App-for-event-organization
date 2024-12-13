package edu.bbte.idde.kmim2248.template;

import com.github.jknack.handlebars.Template;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    Logger logger = LoggerFactory.getLogger(LoginServlet.class);
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "1234";


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("loggedIn") != null) {
            logger.info("User already logged in, redirecting to events page");
            response.sendRedirect("events-html");
            return;
        }

        String error = request.getParameter("error");
        if (error != null) {
            request.setAttribute("error", true);
        }
        Template template = TemplateFactory.getTemplate("login");
        Map<String, Object> data = Map.of("error", error != null);

        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        try (PrintWriter writer = response.getWriter()) {
            writer.write(template.apply(data));
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (USERNAME.equals(username) && PASSWORD.equals(password)) {
            HttpSession session = request.getSession();
            session.setAttribute("loggedIn", true);
            response.sendRedirect("events-html");
        } else {
            response.sendRedirect("login?error=true");
        }
    }
}
