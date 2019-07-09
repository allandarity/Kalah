package uk.co.erlski.kalah.io;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * A DTO object that is used to return errors to the client when they arise
 * @author Elliott Lewandowski
 */
public class ErrorDTO {

    private final String error;
    private final HttpStatus type;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private final LocalDateTime timestamp;

    private ErrorDTO(final String error, final HttpStatus type) {
        this.error = error;
        this.type = type;
        this.timestamp = LocalDateTime.now();
    }

    public static ErrorDTO error(final String error, final HttpStatus type) {
        return new ErrorDTO(error, type);
    }


    public LocalDateTime getTimestamp() {
        return this.timestamp;
    }

    public String getError() {
        return error;
    }

    public HttpStatus getType() {
        return type;
    }
}
