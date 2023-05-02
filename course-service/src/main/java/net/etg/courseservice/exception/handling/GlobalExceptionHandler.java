package net.etg.courseservice.exception.handling;

import net.etg.courseservice.exception.ErrorDetail;
import net.etg.courseservice.exception.NoCourseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoCourseException.class)
    public ResponseEntity<ErrorDetail> handleNoCourseException(WebRequest request,
                                                              NoCourseException exception){

        ErrorDetail error = new ErrorDetail();
        error.setCode("NO_COURSE_ERR");
        error.setMessage(exception.getMessage());
        error.setPath(request.getDescription(false));
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }

}
