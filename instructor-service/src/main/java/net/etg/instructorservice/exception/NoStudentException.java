package net.etg.instructorservice.exception;

public class NoStudentException extends RuntimeException{

    public NoStudentException(){
        super("Student doesn't exist");
    }
}
