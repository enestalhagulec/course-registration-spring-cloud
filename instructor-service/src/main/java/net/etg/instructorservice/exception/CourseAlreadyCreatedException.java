package net.etg.instructorservice.exception;

public class CourseAlreadyCreatedException extends  RuntimeException{

    public CourseAlreadyCreatedException(){
        super("Course already created!");
    }
}
