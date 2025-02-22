package edu.bbte.idde.kmim2248;

public class ErrorMessage {

    private final String error;

    public ErrorMessage(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    @Override
    public String toString() {
        return "error: " + error;
    }
}

