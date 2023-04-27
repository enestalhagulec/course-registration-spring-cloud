package net.etg.instructorservice.dto;

import lombok.Data;

@Data
public class CourseDTO {

    private String courseCode;
    private String name;
    private String description;
    private String instructorId;
}
