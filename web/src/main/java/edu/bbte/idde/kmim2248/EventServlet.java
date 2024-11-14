package edu.bbte.idde.kmim2248;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.bbte.idde.kmim2248.dao.exception.DaoOperationException;
import edu.bbte.idde.kmim2248.dao.exception.EventAlreadyExistsException;
import edu.bbte.idde.kmim2248.dao.exception.EventNotFoundException;
import edu.bbte.idde.kmim2248.dao.impl.EventJdbcDaoImpl;
import edu.bbte.idde.kmim2248.model.Event;
import edu.bbte.idde.kmim2248.service.EventService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static edu.bbte.idde.kmim2248.config.JacksonConfig.getObjectMapper;

@WebServlet("/events")
public class EventServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(EventJdbcDaoImpl.class);

    private EventService eventService;
    private final ObjectMapper mapper = getObjectMapper();


    @Override
    public void init() {
        this.eventService = new EventService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        if (request.getParameter("id") != null) {

            int id = Integer.parseInt(request.getParameter("id"));
            try {
                response.getWriter().write(mapper.writeValueAsString(eventService.findEventById(id)));
            } catch (EventNotFoundException e) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                logger.warn("Event not found", e);
            } catch (DaoOperationException e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                logger.error("Error while getting event", e);
            }
            return;
        }
        try {
            response.getWriter().write(mapper.writeValueAsString(eventService.getAllEvents()));
        } catch (DaoOperationException e) {
            logger.error("Error while getting events", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Event event = mapper.readValue(request.getInputStream(), Event.class);

        if (event.getName() == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        try {
            eventService.createEvent(event);
        } catch (DaoOperationException e) {
            logger.error("Error while creating event", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        } catch (EventAlreadyExistsException e) {
            logger.error("Event already exists", e);
            response.setStatus(HttpServletResponse.SC_CONFLICT);
            return;
        }
        response.setStatus(HttpServletResponse.SC_CREATED);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        int id = Integer.parseInt(request.getParameter("id"));
        try {
            eventService.deleteEvent(id);
        } catch (EventNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            logger.warn("Event not found in servlet", e);
            return;
        } catch (DaoOperationException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            logger.error("Error while deleting event", e);
            return;
        }
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Event event = mapper.readValue(request.getInputStream(), Event.class);
        event.setId(id);
        try {
            eventService.updateEvent(event);
        } catch (EventNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            logger.warn("Event not found in servlet", e);
            return;
        } catch (DaoOperationException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            logger.error("Error while updating event", e);
            return;
        }
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);

    }
}
