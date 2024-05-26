package br.com.ada.locatecar.exceptions;

import br.com.ada.locatecar.payload.response.MessageResponse;
import org.springframework.http.HttpStatus; // Import for more control over status code
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailInUseException.class)
    public ResponseEntity<MessageResponse> handleEmailInUseException(EmailInUseException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new MessageResponse("That email address is already in use. Please try a different one."));
    }

    @ExceptionHandler(DocumentInUseException.class)
    public ResponseEntity<MessageResponse> handleDocumentInUseException(DocumentInUseException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new MessageResponse(ex.getMessage()));
    }
}
