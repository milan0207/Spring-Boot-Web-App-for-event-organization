package edu.bbte.idde.kmim2248.dao.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Database operation failed")
public class DaoOperationException extends Exception {
    public DaoOperationException(String message, Exception e) {
        super(message, e);
    }
}
