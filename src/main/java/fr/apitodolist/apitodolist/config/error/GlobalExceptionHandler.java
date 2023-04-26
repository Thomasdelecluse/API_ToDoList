package fr.apitodolist.apitodolist.config.error;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{
    @ExceptionHandler(FunctionalExeption.class)
    public ResponseEntity<String> handleFunctionalExeption(FunctionalExeption e) {
        HttpStatus httpStatus = e.getHttpStatus();
        return ResponseEntity.status(httpStatus).body(e.getMessage());
    }
}


