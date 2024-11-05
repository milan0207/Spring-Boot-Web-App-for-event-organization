package edu.bbte.idde.kmim2248.dao.exception;


public class DaoOperationException extends Exception {
    public DaoOperationException(String message, Exception e) {
        super(message, e);
    }
}
