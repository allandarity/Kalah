package uk.co.erlski.kalah.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import uk.co.erlski.kalah.io.ErrorDTO;

/**
 * Handles the consuming of KalahExceptions
 * @author Elliott Lewandowski
 */
@ControllerAdvice
public class KalahErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(KalahException.class)
    protected ResponseEntity<Object> handleEntityNotFound(
            KalahException ex) {
        ErrorDTO error = ErrorDTO.error(ex.getMessage(), HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(error.getType()).body(error);
    }


}
