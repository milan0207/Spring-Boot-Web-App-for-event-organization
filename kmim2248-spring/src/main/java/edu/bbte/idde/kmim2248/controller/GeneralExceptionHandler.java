package edu.bbte.idde.kmim2248.controller;

import edu.bbte.idde.kmim2248.dao.exception.DaoOperationException;
import edu.bbte.idde.kmim2248.dao.exception.EventNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GeneralExceptionHandler {

    @ExceptionHandler(EventNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Response handleEventNotFoundException(EventNotFoundException e) {
        return new Response(e.getMessage(), HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(DaoOperationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Response handleDaoOperationException(DaoOperationException e) {
        return new Response(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    @ExceptionHandler(InvalidEventExcpetion.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Response handleInvalidEventException(InvalidEventExcpetion e) {
        return new Response(e.getMessage(), HttpStatus.BAD_REQUEST.value());
    }

}
