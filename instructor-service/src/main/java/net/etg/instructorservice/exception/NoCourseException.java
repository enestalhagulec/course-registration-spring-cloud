package net.etg.instructorservice.exception;


public class NoCourseException extends RuntimeException {

    public NoCourseException() {
        super("Course doesn't exist");
    }
}
