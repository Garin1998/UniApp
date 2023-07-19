package uniapp.controllers.advice;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import uniapp.models.exceptions.CourseNotFound;
import uniapp.models.exceptions.LecturerNotFound;
import uniapp.models.exceptions.StudentCourseNotFound;
import uniapp.models.exceptions.StudentNotFound;

@ControllerAdvice
public class BasicControllerAdvice {

    @ExceptionHandler({
            CourseNotFound.class,
            LecturerNotFound.class,
            StudentCourseNotFound.class,
            StudentNotFound.class
    })
    public ResponseEntity<GenericErrorDto> handleNotFoundExceptions(RuntimeException ex) {
        return ResponseEntity.badRequest().body(new GenericErrorDto(ex.getMessage()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<GenericErrorDto> handleMessageNotReadableExceptions() {
        return ResponseEntity.badRequest().body(new GenericErrorDto("Malformed JSON"));
    }

}
