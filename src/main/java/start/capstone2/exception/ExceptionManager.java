package start.capstone2.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class ExceptionManager {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<?> handleAppException(AppException e) {
        HttpStatus status = e.getErrorCode().getHttpStatus();
        String message = e.getErrorCode().getMessage().isEmpty() ? e.getMessage() : e.getErrorCode().getMessage();

        return ResponseEntity.status(status)
                .body(Map.of("errorCode", e.getErrorCode().name(), "message", message));
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An error occurred: " + e.getMessage());
    }
}
