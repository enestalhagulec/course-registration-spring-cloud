package net.etg.registerservice.exception;


public class NoCourseException extends RuntimeException {

    public NoCourseException() {
        super("Course doesn't exist");
    }
}
