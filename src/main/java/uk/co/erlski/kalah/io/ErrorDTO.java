package uk.co.erlski.kalah.io;

import org.springframework.http.HttpStatus;

public class ErrorDTO {

    private String error;
    private HttpStatus type;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public HttpStatus getType() {
        return type;
    }

    public void setType(HttpStatus type) {
        this.type = type;
    }
}
