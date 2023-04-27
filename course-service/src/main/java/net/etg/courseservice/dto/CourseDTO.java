package net.etg.courseservice.dto;

import lombok.*;

@Data
public class CourseDTO {

    private String courseCode;
    private String name;
    private String description;
    private String instructorId;
}
