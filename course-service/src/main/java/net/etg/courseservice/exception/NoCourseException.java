package net.etg.courseservice.exception;


public class NoCourseException extends RuntimeException {

    public NoCourseException() {
        super("Course doesn't exist");
    }
}
