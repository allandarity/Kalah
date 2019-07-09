package uk.co.erlski.kalah.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import uk.co.erlski.kalah.io.ErrorDTO;

@ControllerAdvice
public class KalahErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(KalahException.class)
    protected ResponseEntity<Object> handleEntityNotFound(
            KalahException ex) {
        ErrorDTO apiError = ErrorDTO.error(ex.getMessage(), HttpStatus.FORBIDDEN);
        return ResponseEntity.status(apiError.getType()).body(apiError);
    }


}
