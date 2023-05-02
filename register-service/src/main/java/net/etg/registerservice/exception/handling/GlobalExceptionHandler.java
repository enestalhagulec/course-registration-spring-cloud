package net.etg.registerservice.exception.handling;

import net.etg.registerservice.exception.ErrorDetail;
import net.etg.registerservice.exception.NoCourseException;
import net.etg.registerservice.exception.NoStudentException;
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

    @ExceptionHandler(NoStudentException.class)
    public ResponseEntity<ErrorDetail> handleNoStudentException(WebRequest request,
                                                                NoStudentException exception){
        ErrorDetail error = new ErrorDetail();
        error.setCode("NO_STUDENT_ERR");
        error.setMessage(exception.getMessage());
        error.setPath(request.getDescription(false));
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);

    }
}
