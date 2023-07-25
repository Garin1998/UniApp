package uniapp.controllers.advice;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import uniapp.models.exceptions.*;

import java.util.HashMap;
import java.util.Map;

import static uniapp.constants.ErrorMessages.MALFORMED_JSON;

@ControllerAdvice
public class BasicControllerAdvice {

    @ExceptionHandler({
            CourseNotFound.class,
            LecturerNotFound.class,
            StudentCourseNotFound.class,
            StudentNotFound.class,
            RoleNotFound.class,
            UsernameNotFoundException.class
    })
    public ResponseEntity<GenericErrorDto> handleNotFoundExceptions(RuntimeException ex) {

        return ResponseEntity.badRequest().body(new GenericErrorDto(ex.getMessage()));

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<GenericErrorDto> handleMessageNotReadableExceptions() {

        return ResponseEntity.badRequest().body(new GenericErrorDto(MALFORMED_JSON));

    }

}
