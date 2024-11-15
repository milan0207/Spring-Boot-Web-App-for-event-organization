package edu.bbte.idde.kmim2248.template;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.bbte.idde.kmim2248.dao.exception.DaoOperationException;
import edu.bbte.idde.kmim2248.model.Event;
import edu.bbte.idde.kmim2248.service.EventService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.github.jknack.handlebars.Template;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.Map;
import static edu.bbte.idde.kmim2248.config.JacksonConfig.getObjectMapper;
import static edu.bbte.idde.kmim2248.service.EventServiceFactory.getEventService;

@WebServlet("/events-html")
public class ListEventsServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(ListEventsServlet.class);

    EventService eventService;

    @Override
    public void init() {
        this.eventService = getEventService();
    }



    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        Map<Integer, Event> events;

        try {
            events = eventService.getAllEvents();
        } catch (DaoOperationException e) {
            logger.error("Error while getting events", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error while getting events");
            return;

        }
        logger.info("Events: {}", events);

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
