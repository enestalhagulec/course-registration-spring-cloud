package net.etg.courseservice.exception;

public class NoInstructorException extends RuntimeException{

    public NoInstructorException(){
        super("Instructor doesn't exist");
    }
}
