package net.etg.courseservice.exception.handling;

import net.etg.courseservice.exception.ErrorDetail;
import net.etg.courseservice.exception.NoCourseException;
import net.etg.courseservice.exception.NoInstructorException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoCourseException.class)
    public ResponseEntity<ErrorDetail> treatNoCourseException(WebRequest request,
                                                              NoCourseException exception){

        ErrorDetail error = new ErrorDetail();
        error.setCode("NO_COURSE_ERR");
        error.setMessage(exception.getMessage());
        error.setPath(request.getDescription(false));
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }

    @ExceptionHandler(NoInstructorException.class)
    public ResponseEntity<ErrorDetail> treatNoInstructorException(WebRequest request,
                                                              NoInstructorException exception){

        ErrorDetail error = new ErrorDetail();
        error.setCode("NO_INSTRUCTOR_ERR");
        error.setMessage(exception.getMessage());
        error.setPath(request.getDescription(false));
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }
}
