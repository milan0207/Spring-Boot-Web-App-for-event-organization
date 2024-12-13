package edu.bbte.idde.kmim2248;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.bbte.idde.kmim2248.dao.exception.DaoOperationException;
import edu.bbte.idde.kmim2248.dao.exception.EventAlreadyExistsException;
import edu.bbte.idde.kmim2248.dao.exception.EventNotFoundException;
import edu.bbte.idde.kmim2248.model.Event;
import edu.bbte.idde.kmim2248.service.EventService;
import edu.bbte.idde.kmim2248.service.EventServiceFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(EventServlet.class);

    private transient EventService eventService;
    private final ObjectMapper mapper = getObjectMapper();

    @Override
    public void init() {
        this.eventService = EventServiceFactory.getEventService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        if (request.getParameter("id") != null) {

            int id = Integer.parseInt(request.getParameter("id"));
            try {
                Object event = eventService.findEventById(id);
                mapper.writeValue(response.getOutputStream(), event);
            } catch (EventNotFoundException e) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                ErrorMessage errorMessage = new ErrorMessage("Event not found");
                logger.warn(errorMessage.toString());
                mapper.writeValue(response.getOutputStream(), errorMessage);

            } catch (DaoOperationException e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                ErrorMessage errorMessage = new ErrorMessage("Error while getting event");
                mapper.writeValue(response.getOutputStream(), errorMessage);
                logger.error(errorMessage.toString(), e);
            }
            return;
        }
        try {
            Object events = eventService.getAllEvents();
            mapper.writeValue(response.getOutputStream(), events);
        } catch (DaoOperationException e) {
            ErrorMessage errorMessage = new ErrorMessage("Error while getting events");
            mapper.writeValue(response.getOutputStream(), errorMessage);
            logger.error(errorMessage.toString(), e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Event event;
        try {
            event = mapper.readValue(request.getInputStream(), Event.class);
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        if (event.getName() == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        try {
            eventService.createEvent(event);
        } catch (DaoOperationException e) {
            ErrorMessage errorMessage = new ErrorMessage("Error while creating event");
            mapper.writeValue(response.getOutputStream(), errorMessage);
            logger.error(errorMessage.toString(), e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        } catch (EventAlreadyExistsException e) {
            ErrorMessage errorMessage = new ErrorMessage("Event already exists");
            mapper.writeValue(response.getOutputStream(), errorMessage);
            logger.warn(errorMessage.toString(), e);
            response.setStatus(HttpServletResponse.SC_CONFLICT);
            return;
        }
        response.setStatus(HttpServletResponse.SC_CREATED);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id;
        try {
            id = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            ErrorMessage errorMessage = new ErrorMessage("Invalid id");
            mapper.writeValue(response.getOutputStream(), errorMessage);
            logger.warn(errorMessage.toString(), e);
            return;
        }

        try {
            eventService.deleteEvent(id);
        } catch (EventNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            ErrorMessage errorMessage = new ErrorMessage("Event not found");
            mapper.writeValue(response.getOutputStream(), errorMessage);
            logger.warn(errorMessage.toString(), e);
            return;
        } catch (DaoOperationException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            ErrorMessage errorMessage = new ErrorMessage("Error while deleting event");
            mapper.writeValue(response.getOutputStream(), errorMessage);
            logger.error(errorMessage.toString(), e);
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
            ErrorMessage errorMessage = new ErrorMessage("Event not found");
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            mapper.writeValue(response.getOutputStream(), errorMessage);
            logger.warn(errorMessage.toString(), e);
            return;
        } catch (DaoOperationException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            ErrorMessage errorMessage = new ErrorMessage("Error while updating event");
            mapper.writeValue(response.getOutputStream(), errorMessage);
            logger.error(errorMessage.toString(), e);
            return;
        }
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }
}
