package edu.bbte.idde.kmim2248.dao.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Event not found")
public class EventNotFoundException extends Exception {

    public EventNotFoundException(String message) {
        super(message);
    }
}
