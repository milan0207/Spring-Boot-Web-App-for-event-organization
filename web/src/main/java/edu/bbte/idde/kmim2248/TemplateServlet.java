package edu.bbte.idde.kmim2248;

import edu.bbte.idde.kmim2248.service.EventService;
import freemarker.template.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.*;

@WebServlet("/entities")
public class TemplateServlet extends HttpServlet {
    private Configuration cfg;

    @Override
    public void init() {
        cfg = new Configuration(Configuration.VERSION_2_3_32);
        cfg.setServletContextForTemplateLoading(getServletContext(), "/templates");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        Map<String, Object> data = new HashMap<>();
        EventService eventService = new EventService();


        Template template = cfg.getTemplate("entities.ftl");
        try {
            template.process(data, response.getWriter());
        } catch (TemplateException e) {
            throw new IOException(e);
        }
    }
}
