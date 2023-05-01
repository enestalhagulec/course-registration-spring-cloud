package net.etg.instructorservice.exception;

public class NoInstructorException extends RuntimeException{

    public NoInstructorException(){
        super("Instructor doesn't exist");
    }
}
