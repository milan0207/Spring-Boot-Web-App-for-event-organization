package edu.bbte.idde.kmim2248.template;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.bbte.idde.kmim2248.model.Event;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.github.jknack.handlebars.Template;

import java.io.IOException;
import java.io.PrintWriter;

import java.net.URL;
import java.util.Map;

import static edu.bbte.idde.kmim2248.config.JacksonConfig.getObjectMapper;

@WebServlet("/events-html")
public class ListEventsServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(ListEventsServlet.class);

    private final ObjectMapper mapper = getObjectMapper();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        Map<Integer, Event> events;
        try {
            events = mapper.readValue(new URL("http://localhost:8080/event/events").openStream(),
                    new TypeReference<>() {
                    });
        } catch (Exception e) {
            logger.error("Error while fetching events", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to fetch events");
            return;
        }

        Template template;
        try {
            template = TemplateFactory.getTemplate("events");
        } catch (IOException e) {
            logger.error("Error loading Handlebars template", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Template not found");
            return;
        }

        Map<String, Object> data = Map.of("events", events.values());

        try (PrintWriter writer = response.getWriter()) {
            writer.write(template.apply(data));
        } catch (IOException e) {
            logger.error("Error rendering template", e);
        }
    }
}
